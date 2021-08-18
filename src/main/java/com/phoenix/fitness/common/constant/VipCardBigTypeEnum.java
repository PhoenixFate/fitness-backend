package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * vip卡大分类
 */
@Getter
@AllArgsConstructor
public enum VipCardBigTypeEnum {
    /**
     * 阶段卡
     */
    DURATION_CARD(1, "DURATION_CARD"),
    /**
     * 次卡
     */
    COUNT_CARD(2, "COUNT_CARD");

    private final int cardTypeCode;
    private final String cardTypeName;
}
