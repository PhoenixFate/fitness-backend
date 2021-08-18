package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 顾客训练中的每组训练数据
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Data
@TableName("tb_action_set_customer_one_set")
public class ActionSetCustomerOneSetEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long oneSetId;
    /**
     * 第几组
     */
    private Integer setIndex;
    /**
     * 关联的上级动作
     */
    @JsonIgnore
    private Long actionSetCustomerId;
    /**
     * 关联的顾客训练id
     */
    @JsonIgnore
    private Long customerPlanDayId;
    /**
     * 状态
     * 0: 未开始
     * 1：正在进行
     * 2：已暂停
     * 3：已完成
     */
    private Integer status;
    /**
     * 耗时-分钟
     */
    private Integer minute;
    /**
     * 耗时-秒
     */
    private Integer second;
    /**
     * 耗时-毫秒
     */
    private Integer microSecond;
    /**
     * 实际的休息时间
     */
    private Integer realRestTime;

    @TableField(exist = false)
    private List<ActionSetCustomerOneSetDetailEntity> oneSetDetails = new ArrayList<>();
}
