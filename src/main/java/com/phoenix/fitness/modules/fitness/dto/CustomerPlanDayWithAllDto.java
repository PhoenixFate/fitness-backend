package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanDayEntity;
import lombok.Data;

@Data
public class CustomerPlanDayWithAllDto extends CustomerPlanDayEntity {
    /**
     * 具体课程
     */
    private ClassInfoWithExercisesCustomerDto classInfo;
    /**
     * 顾客
     */
    private CustomerEntity customer;
    /**
     * 顾客训练计划
     */
    private CustomerPlanWithTrainingPlanDto customerPlan;
}
