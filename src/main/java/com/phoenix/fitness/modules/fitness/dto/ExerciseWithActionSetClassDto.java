package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.ExerciseEntity;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ExerciseWithActionSetClassDto extends ExerciseEntity {
    /**
     * 动作组
     */
    @Valid
    @NotNull(message = "动作组不能为空")
    private List<ActionSetInClassDto> actionSets;

}
