package com.phoenix.fitness.modules.fitness.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.phoenix.fitness.modules.fitness.entity.ActionSetActionInCustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.ActionSetCustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.ActionSetCustomerOneSetEntity;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 训练项中的动作组
 */
@Data
public class ActionSetInCustomerDto extends ActionSetCustomerEntity {
    /**
     * 训练项中的实例(顾客训练中的实例)
     * 一个动作组多个动作
     */
    @NotNull(message = "动作组中的动作不能为空")
    @Valid
    private List<ActionSetActionInCustomerEntity> actions;
    /**
     * 动作组中的所有顾客的照片
     */
    private List<String> imageUrls = new ArrayList<>();
    /**
     * 实际训练的每组详情数据
     */
    @TableField(exist = false)
    private List<ActionSetCustomerOneSetEntity> oneSets;
}
