package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import com.phoenix.fitness.modules.fitness.service.ActionSetActionService;
import com.phoenix.fitness.modules.fitness.service.ActionSetService;
import com.phoenix.fitness.modules.fitness.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ActionSetTypeEnum;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.ActionSetActionDao;
import com.phoenix.fitness.modules.fitness.dao.ActionSetDao;
import com.phoenix.fitness.modules.fitness.dao.ExerciseDao;
import com.phoenix.fitness.modules.fitness.dto.ActionSetInExerciseDto;
import com.phoenix.fitness.modules.fitness.dto.ExerciseWithActionSetDto;
import com.phoenix.fitness.modules.fitness.entity.ActionSetActionEntity;
import com.phoenix.fitness.modules.fitness.entity.ActionSetEntity;
import com.phoenix.fitness.modules.fitness.entity.ExerciseEntity;
import com.phoenix.fitness.modules.fitness.service.*;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("exerciseService")
@AllArgsConstructor
public class ExerciseServiceImpl extends ServiceImpl<ExerciseDao, ExerciseEntity> implements ExerciseService {

    private final ExerciseDao exerciseDao;

    private final ActionSetDao actionSetDao;

    private final ActionSetActionDao actionSetActionDao;

    private final ActionSetActionService actionSetActionService;

    private final ActionSetService actionSetService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, UserEntity user) {
        QueryWrapper<ExerciseEntity> exerciseQueryWrapper = new QueryWrapper<>();
        String searchValue = (String) params.get("searchValue");
        if (!StringUtils.isEmpty(searchValue)) {
            exerciseQueryWrapper.and(wrapper -> wrapper.like("exercise_name", searchValue));
        }
        if (user != null) {
            if (user.getGymId() != null) {
                exerciseQueryWrapper.eq("gym_id", user.getGymId());
            }
            if (user.getPartnerId() != null) {
                exerciseQueryWrapper.eq("partner_id", user.getPartnerId());
            }
        }
        IPage<ExerciseEntity> page = this.page(
                new Query<ExerciseEntity>().getPage(params),
                exerciseQueryWrapper.eq("delete_flag", 0).orderByDesc("create_time")
        );
        List<ExerciseEntity> records = page.getRecords();
        List<ExerciseEntity> list = new ArrayList<>();
        for (ExerciseEntity temp : records) {
            ExerciseEntity exerciseWithActionSetDto = exerciseDao.selectExerciseWithActionSet(temp.getExerciseId());
            list.add(exerciseWithActionSetDto);
        }
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        ExerciseEntity temp = exerciseDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = exerciseDao.deleteByDeleteFlag(id);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return null;
    }

    @Override
    public ExerciseWithActionSetDto getDetail(Long id) {
        ExerciseWithActionSetDto exercise = exerciseDao.selectExerciseWithActionSet(id);
        if (exercise == null || exercise.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return exercise;
    }

    @Override
    @Transactional
    public Boolean save(ExerciseWithActionSetDto exercise) {
        int count = exerciseDao.insert(exercise);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        if (!CollectionUtils.isEmpty(exercise.getActionSets())) {
            for (int i = 0; i < exercise.getActionSets().size(); i++) {
                ActionSetInExerciseDto actionSetEntity = exercise.getActionSets().get(i);
                actionSetEntity.setExerciseId(exercise.getExerciseId());
                //检查动作组类型
                if (!(actionSetEntity.getActionSetType().equals(ActionSetTypeEnum.COMMON_SET.getStatusName())
                        || (actionSetEntity.getActionSetType().equals(ActionSetTypeEnum.SUPER_SET.getStatusName()))
                        || (actionSetEntity.getActionSetType().equals(ActionSetTypeEnum.REDUCE_SET.getStatusName()))
                )) {
                    //动作组类型不对
                    throw new FitnessException(ExceptionEnum.ACTION_SET_TYPE_ERROR);
                }
                //递减组、递减组数不能为空
                if (actionSetEntity.getActionSetType().equals(ActionSetTypeEnum.REDUCE_SET.getStatusName())) {
                    if (actionSetEntity.getReduceSetNumber() == null) {
                        //递减组数不能为空
                        throw new FitnessException(ExceptionEnum.REDUCE_SET_NUMBER_IS_NULL);
                    }
                }
                actionSetEntity.setSort(i);
                actionSetDao.insert(actionSetEntity);
                if (CollectionUtils.isEmpty(actionSetEntity.getActions())) {
                    //动作组中没有动作
                    throw new FitnessException(ExceptionEnum.ACTION_SET_ACTION_ERROR);
                } else {
                    for(ActionSetActionEntity action:actionSetEntity.getActions()){
                        action.setExerciseId(exercise.getExerciseId());
                        action.setActionSetId(actionSetEntity.getActionSetId());
                        action.setSort(actionSetEntity.getActions().indexOf(action));
                        actionSetActionDao.insert(action);
                    }
                }
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean updateById(ExerciseWithActionSetDto exercise) {
        int count = exerciseDao.updateById(exercise);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        List<Long> actionSetIds = new ArrayList<>();
        for (int i = 0; i < exercise.getActionSets().size(); i++) {
            ActionSetEntity actionSetEntity = exercise.getActionSets().get(i);
            actionSetEntity.setExerciseId(exercise.getExerciseId());
            if (actionSetEntity.getActionSetId() != null) {
                actionSetIds.add(actionSetEntity.getActionSetId());
            }
            actionSetEntity.setSort(i);
        }
        if (actionSetIds.size() > 0) {
            actionSetDao.delete(new QueryWrapper<ActionSetEntity>().notIn("action_set_id", actionSetIds).eq("exercise_id", exercise.getExerciseId()));
        } else {
            actionSetDao.delete(new QueryWrapper<ActionSetEntity>().eq("exercise_id", exercise.getExerciseId()));
        }
        actionSetService.saveOrUpdateBatch((List) exercise.getActionSets());
        //删除动作组中动作详情数据
        actionSetActionDao.delete(new QueryWrapper<ActionSetActionEntity>().eq("exercise_id", exercise.getExerciseId()));
        List<ActionSetActionEntity> actionSetActions = new ArrayList<>();
        for (ActionSetInExerciseDto actionSetEntity : exercise.getActionSets()) {
            for (int i = 0; i < actionSetEntity.getActions().size(); i++) {
                ActionSetActionEntity action = actionSetEntity.getActions().get(i);
                action.setActionSetId(actionSetEntity.getActionSetId());
                action.setExerciseId(exercise.getExerciseId());
                action.setSort(i);
                actionSetActions.add(action);
            }
        }
        actionSetActionService.saveOrUpdateBatch(actionSetActions);
        return true;
    }

    @Override
    public List<ExerciseWithActionSetDto> listWithDetail(UserEntity user) {
        return exerciseDao.selectListWithDetail(user);
    }

}