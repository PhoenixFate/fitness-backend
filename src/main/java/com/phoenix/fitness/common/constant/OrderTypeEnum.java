package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单分类
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {
    /**
     * 开通会员卡
     */
    OPEN_VIP(1,"OPEN_VIP"),
    /**
     * 根据课程单价*总价开通私教课
     */
    OPEN_CLASS_BY_TOTAL(2,"OPEN_CLASS_BY_TOTAL"),
    /**
     * 根据训练计划套餐费用开通私教课
     */
    OPEN_CLASS_BY_TRAINING_PLAN(3, "OPEN_CLASS_BY_TRAINING_PLAN");

    private final int typeCode;
    private final String statusName;
}
