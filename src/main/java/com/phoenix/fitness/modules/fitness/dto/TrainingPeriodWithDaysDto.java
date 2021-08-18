package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.TrainingPeriodEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TrainingPeriodWithDaysDto extends TrainingPeriodEntity {
    /**
     * 一个训练阶段中的具体的哪些天的训练
     */
    @NotNull(message = "训练阶段中的具体的训练不能为空")
    private List<TrainingPeriodDayWithClassDto> trainingPeriodDays;

}
