package com.phoenix.fitness.modules.fitness.dto;

import lombok.Data;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanDayEntity;

@Data
public class CustomerPlanDayWithClassInfoDto extends CustomerPlanDayEntity {
    /**
     * 具体课程
     */
    private ClassInfoWithExercisesDto classInfo;
}
