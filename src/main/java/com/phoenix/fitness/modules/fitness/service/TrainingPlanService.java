package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.TrainingPlanWithPeriodsDto;
import com.phoenix.fitness.modules.fitness.entity.TrainingPlanEntity;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 训练计划service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
public interface TrainingPlanService extends IService<TrainingPlanEntity> {

    PageUtils queryPage(Map<String, Object> params, UserEntity user);

    List<TrainingPlanWithPeriodsDto> selectTrainingPlanListWithDetail(UserEntity user);

    Boolean removeByDeleteFlag(Long ids);

    TrainingPlanWithPeriodsDto getDetail(Long id);

    Boolean save(TrainingPlanWithPeriodsDto trainingPlan);

    Boolean updateById(TrainingPlanWithPeriodsDto trainingPlan);
}

