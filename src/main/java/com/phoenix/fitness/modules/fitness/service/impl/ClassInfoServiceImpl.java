package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import com.phoenix.fitness.modules.fitness.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.ActionSetActionInClassDao;
import com.phoenix.fitness.modules.fitness.dao.ActionSetClassDao;
import com.phoenix.fitness.modules.fitness.dao.ClassExerciseDao;
import com.phoenix.fitness.modules.fitness.dao.ClassInfoDao;
import com.phoenix.fitness.modules.fitness.dto.ActionSetInClassDto;
import com.phoenix.fitness.modules.fitness.dto.ClassInfoWithExercisesDto;
import com.phoenix.fitness.modules.fitness.dto.ExerciseWithActionSetClassDto;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.service.ActionSetActionInClassService;
import com.phoenix.fitness.modules.fitness.service.ActionSetClassService;
import com.phoenix.fitness.modules.fitness.service.ClassExerciseService;
import com.phoenix.fitness.modules.fitness.service.ClassInfoService;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("classInfoService")
@AllArgsConstructor
public class ClassInfoServiceImpl extends ServiceImpl<ClassInfoDao, ClassInfoEntity> implements ClassInfoService {

    private final ClassInfoDao classInfoDao;

    private final ClassExerciseDao classExerciseDao;

    private final ClassExerciseService classExerciseService;

    private final ActionSetActionInClassDao actionSetActionInClassDao;

    private final ActionSetActionInClassService actionSetActionInClassService;

    private final ActionSetClassDao actionSetClassDao;

    private final ActionSetClassService actionSetClassService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, UserEntity user) {
        QueryWrapper<ClassInfoEntity> classInfoQueryWrapper = new QueryWrapper<>();
        String searchValue = (String) params.get("searchValue");
        if (!StringUtils.isEmpty(searchValue)) {
            classInfoQueryWrapper.and(wrapper -> wrapper.like("class_name", searchValue));
        }
        if (user != null) {
            if (user.getGymId() != null) {
                classInfoQueryWrapper.eq("gym_id", user.getGymId());
            }
            if (user.getPartnerId() != null) {
                classInfoQueryWrapper.eq("partner_id", user.getPartnerId());
            }
        }
        IPage<ClassInfoEntity> page = this.page(
                new Query<ClassInfoEntity>().getPage(params),
                classInfoQueryWrapper.eq("delete_flag", 0).orderByDesc("create_time")
        );
        List<ClassInfoEntity> classInfoList = page.getRecords();
        List<ClassInfoEntity> finalClassInfoList = new ArrayList<>();
        for (ClassInfoEntity classInfoEntity : classInfoList) {
            ClassInfoEntity classInfoWithExercisesDto = classInfoDao.selectClassInfoWithDetail(classInfoEntity.getClassId());
            finalClassInfoList.add(classInfoWithExercisesDto);
        }
        page.setRecords(finalClassInfoList);
        return new PageUtils(page);
    }

    @Override
    public List<ClassInfoWithExercisesDto> selectListWithDetail(UserEntity user) {
        return classInfoDao.selectClassInfoListWithDetail(user);
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        ClassInfoEntity temp = classInfoDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = classInfoDao.deleteByDeleteFlag(id);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }

    @Override
    public ClassInfoWithExercisesDto getDetail(Long id) {
        ClassInfoWithExercisesDto classInfo = classInfoDao.selectClassInfoWithDetail(id);
        if (classInfo == null || classInfo.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }

        return classInfo;
    }

    @Override
    public Boolean save(ClassInfoWithExercisesDto classInfo) {
        int count = classInfoDao.insert(classInfo);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(classInfo);
        return true;
    }

    @Override
    public Boolean updateById(ClassInfoWithExercisesDto classInfo) {
        ClassInfoEntity temp = classInfoDao.selectById(classInfo.getClassId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = classInfoDao.updateById(classInfo);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.saveOrUpdateBase(classInfo);
        return true;
    }

    public void saveOrUpdateBase(ClassInfoWithExercisesDto classInfo) {
        //删除课程-训练项 关系
        classExerciseDao.delete(new QueryWrapper<ClassExerciseRelationEntity>().eq("class_id", classInfo.getClassId()));
        //维护课程-训练项 关联关系
        List<ClassExerciseRelationEntity> classExerciseRelations = new ArrayList<>();
        for (ExerciseEntity exerciseEntity : classInfo.getExercises()) {
            ClassExerciseRelationEntity classExerciseRelationEntity = new ClassExerciseRelationEntity();
            classExerciseRelationEntity.setExerciseId(exerciseEntity.getExerciseId());
            classExerciseRelationEntity.setClassId(classInfo.getClassId());
            classExerciseRelations.add(classExerciseRelationEntity);
        }
        classExerciseService.saveOrUpdateBatch(classExerciseRelations);

        //重新拷贝训练项目中的actionSet，使之成为class中的实例actionSetClass
        actionSetClassDao.delete(new QueryWrapper<ActionSetClassEntity>().eq("class_id", classInfo.getClassId()));
        List<ActionSetClassEntity> actionSetClassList = new ArrayList<>();
        for (ExerciseWithActionSetClassDto exerciseEntity : classInfo.getExercises()) {
            for (ActionSetInClassDto actionSet : exerciseEntity.getActionSets()) {
                actionSet.setClassId(classInfo.getClassId());
                actionSet.setExerciseId(exerciseEntity.getExerciseId());
                actionSetClassList.add(actionSet);
            }
        }
        actionSetClassService.saveOrUpdateBatch(actionSetClassList);

        //删除课程详细数据
        actionSetActionInClassDao.delete(new QueryWrapper<ActionSetActionInClassEntity>().eq("class_id", classInfo.getClassId()));
        List<ActionSetActionInClassEntity> actionSetActionInClassList = new ArrayList<>();
        for (ExerciseWithActionSetClassDto exercise : classInfo.getExercises()) {
            for (ActionSetInClassDto actionSet : exercise.getActionSets()) {
                for (int i = 0; i < actionSet.getActions().size(); i++) {
                    ActionSetActionInClassEntity action = actionSet.getActions().get(i);
                    action.setClassId(classInfo.getClassId());
                    action.setActionSetClassId(actionSet.getActionSetClassId());
                    action.setExerciseId(exercise.getExerciseId());
                    action.setSort(i);
                    actionSetActionInClassList.add(action);
                }
            }
        }
        //重新维护课程详细数据
        actionSetActionInClassService.saveBatch(actionSetActionInClassList);
    }

}