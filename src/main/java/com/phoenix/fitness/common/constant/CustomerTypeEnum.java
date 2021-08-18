package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 顾客分类
 */
@Getter
@AllArgsConstructor
public enum CustomerTypeEnum {
    /**
     * 从未消费
     */
    NEVER_CONSUME(1, "NEVER_CONSUME"),
    /**
     * 第一次消费
     */
    FIRST_CONSUME(2, "FIRST_CONSUME"),
    /**
     * 多次消费
     */
    MORE_CONSUME(2, "MORE_CONSUME");

    private final int customerTypeCode;
    private final String customerTypeName;
}
