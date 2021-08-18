package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 目标分类类型
 */
@Getter
@AllArgsConstructor
public enum TargetTypeEnum {
    /**
     * 新增顾客数量
     */
    NEW_CUSTOMER(1,"NEW_CUSTOMER"),
    /**
     * 新增会员数量
     */
    NEW_VIP(2,"NEW_VIP"),
    /**
     * 销售金额
     */
    SALE_MONEY(3, "SALE_MONEY");

    private final int typeCode;
    private final String typeName;
}
