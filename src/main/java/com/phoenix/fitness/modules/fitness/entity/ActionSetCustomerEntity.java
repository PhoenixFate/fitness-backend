package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 动作组(顾客每天训练中的实例)
 * 1节课 : n训练项目 : n动作组
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_action_set_customer")
public class ActionSetCustomerEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long actionSetCustomerId;
    /**
     * 训练项id
     */
    @JsonIgnore
    private Long exerciseId;
    /**
     * 普通组 COMMON_SET
     * 超级组 SUPER_SET
     * 递减组 REDUCE_SET
     */
    private String actionSetType;
    /**
     * 顾客id
     */
    @JsonIgnore
    private Long customerId;
    /**
     * 顾客训练计划id
     */
    @JsonIgnore
    private Long customerPlanId;
    /**
     * 训练日id
     */
    @JsonIgnore
    private Long customerPlanDayId;
    /**
     * 标准的组数
     */
    private Integer standardSetNumber;
    /**
     * 实际的组数
     */
    private Integer realSetNumber;
    /**
     * 标准的递减组数
     */
    private Integer standardReduceSetNumber;
    /**
     * 实际的递减组数(平均值)
     */
    private Integer realReduceSetNumber;
    /**
     * 标准的组间休息时间
     */
    private Integer standardRestInternal;
    /**
     * 实际的组间休息时间(平均值)
     */
    private Integer realRestInternal;
    /**
     * 排序
     * 默认生序
     */
    private Integer sort;
}
