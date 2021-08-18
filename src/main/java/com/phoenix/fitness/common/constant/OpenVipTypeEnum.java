package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开通VIP权限的类型
 */
@Getter
@AllArgsConstructor
public enum OpenVipTypeEnum {
    /**
     * 手动MANUAL
     */
    MANUAL(1,"MANUAL"),
    /**
     * 课程赠送CLASS_SEND
     */
    CLASS_SEND(2,"CLASS_SEND"),
    /**
     * 购买会员卡BUY_VIP_CARD
     */
    BUY_VIP_CARD(3, "BUY_VIP_CARD");

    private final int typeCode;
    private final String statusName;
}
