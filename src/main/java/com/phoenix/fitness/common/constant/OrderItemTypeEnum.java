package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单子项目分类
 */
@Getter
@AllArgsConstructor
public enum OrderItemTypeEnum {
    /**
     * 训练计划：按照整个训练计划付费
     */
    TRAINING_PLAN(1,"TRAINING_PLAN"),
    /**
     * 私教课：按照私教课程单价*数量付费
     */
    COACH_CLASS(2,"COACH_CLASS"),
    /**
     * 会员卡
     */
    VIP_CARD(3, "VIP_CARD");

    private final int typeCode;
    private final String statusName;
}
