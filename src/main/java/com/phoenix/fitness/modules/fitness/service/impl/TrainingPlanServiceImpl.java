package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import com.phoenix.fitness.modules.fitness.dto.ClassInfoWithExercisesDto;
import com.phoenix.fitness.modules.fitness.dto.TrainingPeriodDayWithClassDto;
import com.phoenix.fitness.modules.fitness.dto.TrainingPeriodWithDaysDto;
import com.phoenix.fitness.modules.fitness.dto.TrainingPlanWithPeriodsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.ClassInfoDao;
import com.phoenix.fitness.modules.fitness.dao.TrainingPeriodDao;
import com.phoenix.fitness.modules.fitness.dao.TrainingPeriodDayDao;
import com.phoenix.fitness.modules.fitness.dao.TrainingPlanDao;
import com.phoenix.fitness.modules.fitness.dto.*;
import com.phoenix.fitness.modules.fitness.entity.ClassInfoEntity;
import com.phoenix.fitness.modules.fitness.entity.TrainingPeriodDayEntity;
import com.phoenix.fitness.modules.fitness.entity.TrainingPeriodEntity;
import com.phoenix.fitness.modules.fitness.entity.TrainingPlanEntity;
import com.phoenix.fitness.modules.fitness.service.TrainingPeriodDayService;
import com.phoenix.fitness.modules.fitness.service.TrainingPeriodService;
import com.phoenix.fitness.modules.fitness.service.TrainingPlanService;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("trainingPlanService")
@AllArgsConstructor
public class TrainingPlanServiceImpl extends ServiceImpl<TrainingPlanDao, TrainingPlanEntity> implements TrainingPlanService {

    private final TrainingPlanDao trainingPlanDao;

    private final ClassInfoDao classInfoDao;

    private final TrainingPeriodService trainingPeriodService;

    private final TrainingPeriodDao trainingPeriodDao;

    private final TrainingPeriodDayService trainingPeriodDayService;

    private final TrainingPeriodDayDao trainingPeriodDayDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params, UserEntity user) {
        String searchValue = "";
        if (!StringUtils.isEmpty(params.get("searchValue"))) {
            searchValue = (String) params.get("searchValue");
        }
        IPage<TrainingPlanEntity> pageParams = new Query<TrainingPlanEntity>().getPage(params);
        IPage<TrainingPlanEntity> trainingPlanEntityIPage = trainingPlanDao.selectPlanWithGoal(pageParams, searchValue, user);
        return new PageUtils(trainingPlanEntityIPage);
    }

    @Override
    public List<TrainingPlanWithPeriodsDto> selectTrainingPlanListWithDetail(UserEntity user) {
        List<TrainingPlanWithPeriodsDto> trainingPlans = trainingPlanDao.selectPlanListWithDetail(user);
        //给每一天的训练添加课程信息
        List<ClassInfoWithExercisesDto> classInfoEntityList = classInfoDao.selectClassInfoListWithDetail(new UserEntity());
        for (TrainingPlanWithPeriodsDto trainingPlan : trainingPlans) {
            for (TrainingPeriodWithDaysDto trainingPeriod : trainingPlan.getTrainingPeriods()) {
                for (TrainingPeriodDayWithClassDto trainingPeriodDay : trainingPeriod.getTrainingPeriodDays()) {
                    if (trainingPeriodDay.getClassId() != null) {
                        for (ClassInfoEntity classInfoEntity : classInfoEntityList) {
                            if (classInfoEntity.getClassId().equals(trainingPeriodDay.getClassId())) {
                                trainingPeriodDay.setClassInfo(classInfoEntity);
                            }
                        }
                    }
                }
            }
        }
        return trainingPlans;
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        TrainingPlanEntity temp = trainingPlanDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = trainingPlanDao.deleteByDeleteFlag(id);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }

    @Override
    public TrainingPlanWithPeriodsDto getDetail(Long id) {
        TrainingPlanWithPeriodsDto trainingPlan = trainingPlanDao.selectPlanWithDetail(id);
        if (trainingPlan == null || trainingPlan.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return trainingPlan;
    }

    @Override
    public Boolean save(TrainingPlanWithPeriodsDto trainingPlan) {
        int count = trainingPlanDao.insert(trainingPlan);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(trainingPlan);
        return true;
    }

    @Override
    public Boolean updateById(TrainingPlanWithPeriodsDto trainingPlan) {
        TrainingPlanEntity temp = trainingPlanDao.selectById(trainingPlan.getTrainingPlanId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = trainingPlanDao.updateById(trainingPlan);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.saveOrUpdateBase(trainingPlan);
        return true;
    }

    public void saveOrUpdateBase(TrainingPlanWithPeriodsDto trainingPlan) {
        //重新维护 训练-训练阶段 的关系
        trainingPeriodDao.delete(new QueryWrapper<TrainingPeriodEntity>().eq("training_plan_id", trainingPlan.getTrainingPlanId()));
        List<TrainingPeriodEntity> trainingPeriods = new ArrayList<>();
        for (TrainingPeriodEntity trainingPeriod : trainingPlan.getTrainingPeriods()) {
            trainingPeriod.setTrainingPlanId(trainingPlan.getTrainingPlanId());
            trainingPeriods.add(trainingPeriod);
        }
        trainingPeriodService.saveOrUpdateBatch(trainingPeriods);

        //重新维护 训练阶段-训练日 的关系
        trainingPeriodDayDao.delete(new QueryWrapper<TrainingPeriodDayEntity>().eq("training_plan_id", trainingPlan.getTrainingPlanId()));
        List<TrainingPeriodDayEntity> trainingPeriodDays = new ArrayList<>();
        for (int i = 0; i < trainingPlan.getTrainingPeriods().size(); i++) {
            TrainingPeriodWithDaysDto trainingPeriod = trainingPlan.getTrainingPeriods().get(i);
            trainingPeriod.setPeriodIndex(i);
            for (int j = 0; j < trainingPeriod.getTrainingPeriodDays().size(); j++) {
                TrainingPeriodDayWithClassDto trainingPeriodDay = trainingPeriod.getTrainingPeriodDays().get(j);
                trainingPeriodDay.setTrainingPeriodId(trainingPeriod.getTrainingPeriodId());
                trainingPeriodDay.setTrainingPlanId(trainingPlan.getTrainingPlanId());
                trainingPeriodDay.setDayIndex(j);
                if (trainingPeriodDay.getClassInfo() == null) {
                    trainingPeriodDay.setClassId(null);
                } else {
                    trainingPeriodDay.setClassId(trainingPeriodDay.getClassInfo().getClassId());
                }
                trainingPeriodDays.add(trainingPeriodDay);
            }
        }
        trainingPeriodDayService.saveOrUpdateBatch(trainingPeriodDays);
    }


}