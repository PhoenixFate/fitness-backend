package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CustomerPlanEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanPeriodEntity;
import com.phoenix.fitness.modules.fitness.entity.TrainingPlanEntity;
import lombok.Data;

import java.util.List;

@Data
public class CustomerPlanWithAllDto extends CustomerPlanEntity {
    /**
     * 训练计划
     */
    private TrainingPlanEntity trainingPlan;
    /**
     * 教练姓名
     */
    private String coachName;
    /**
     * 训练阶段（顾客实例）
     */
    private List<CustomerPlanPeriodEntity> customerPlanPeriods;
}
