package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.TrainingPlanEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TrainingPlanWithPeriodsDto extends TrainingPlanEntity {
    /**
     * 训练计划阶段
     */
    @NotNull(message = "训练计划阶段不能为空")
    private List<TrainingPeriodWithDaysDto> trainingPeriods;
}
