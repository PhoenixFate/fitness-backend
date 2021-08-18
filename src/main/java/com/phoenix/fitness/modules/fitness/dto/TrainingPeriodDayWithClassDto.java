package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.ClassInfoEntity;
import com.phoenix.fitness.modules.fitness.entity.TrainingPeriodDayEntity;
import lombok.Data;

@Data
public class TrainingPeriodDayWithClassDto extends TrainingPeriodDayEntity {
    /**
     * 训练关联的课程
     */
    private ClassInfoEntity classInfo;
}
