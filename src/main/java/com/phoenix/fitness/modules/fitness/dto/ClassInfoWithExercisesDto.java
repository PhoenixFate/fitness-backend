package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.ClassInfoEntity;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ClassInfoWithExercisesDto extends ClassInfoEntity {
    /**
     * 课程关联的训练项
     */
    @NotNull(message = "训练项不能为空")
    @Valid
    List<ExerciseWithActionSetClassDto> exercises;
}
