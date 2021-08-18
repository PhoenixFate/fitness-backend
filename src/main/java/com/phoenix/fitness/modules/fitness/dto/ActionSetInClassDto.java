package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.ActionSetActionInClassEntity;
import com.phoenix.fitness.modules.fitness.entity.ActionSetClassEntity;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 训练项中的动作组
 */
@Data
public class ActionSetInClassDto extends ActionSetClassEntity {
    /**
     * 训练项中的实例
     * 一个动作组多个动作
     */
    @NotNull(message = "动作组中的动作不能为空")
    @Valid
    private List<ActionSetActionInClassEntity> actions;
}
