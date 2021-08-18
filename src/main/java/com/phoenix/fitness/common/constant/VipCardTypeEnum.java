package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * vip卡分类
 */
@Getter
@AllArgsConstructor
public enum VipCardTypeEnum {
    /**
     * 周卡
     */
    WEEK_CARD(1, "WEEK_CARD"),
    /**
     * 月卡
     */
    MONTH_CARD(2, "MONTH_CARD"),
    /**
     * 季卡
     */
    SEASON_CARD(3, "SEASON_CARD"),
    /**
     * 半年卡
     */
    HALF_YEAR_CARD(4, "HALF_YEAR_CARD"),
    /**
     * 三季度卡
     */
    THREE_SEASON_CARD(5, "THREE_SEASON_CARD"),
    /**
     * 年卡
     */
    YEAR_CARD(6, "YEAR_CARD"),
    /**
     * 老会员卡
     */
    OLD_VIP_CARD(7, "OLD_VIP_CARD");

    private final int typeCode;
    private final String statusName;
}
