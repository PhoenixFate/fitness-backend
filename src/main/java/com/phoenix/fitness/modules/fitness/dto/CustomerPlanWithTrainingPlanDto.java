package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CustomerPlanEntity;
import com.phoenix.fitness.modules.fitness.entity.TrainingPlanEntity;
import lombok.Data;

@Data
public class CustomerPlanWithTrainingPlanDto extends CustomerPlanEntity {
    /**
     * 训练计划
     */
    private TrainingPlanEntity trainingPlan;
}
