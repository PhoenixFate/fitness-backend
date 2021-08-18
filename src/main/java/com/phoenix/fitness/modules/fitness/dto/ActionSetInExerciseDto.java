package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.ActionSetActionEntity;
import com.phoenix.fitness.modules.fitness.entity.ActionSetEntity;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * 训练项中的动作组
 */
@Data
public class ActionSetInExerciseDto extends ActionSetEntity {
    /**
     * 训练项中的实例
     * 一个动作组多个动作
     */
    @Valid
    private List<ActionSetActionEntity> actions;
}
