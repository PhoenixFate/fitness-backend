package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式
 */
@Getter
@AllArgsConstructor
public enum PayWayEnum {
    /**
     * 支付宝-对公
     */
    ALIPAY(1, "ALIPAY"),
    /**
     * 微信-对公
     */
    WECHAT(2, "WECHAT_PAY"),
    /**
     * 对公账户
     */
    PUBLIC_ACCOUNT(3, "PUBLIC_ACCOUNT"),
    /**
     * 大众点评
     */
    PUBLIC_COMMENT(4, "PUBLIC_COMMENT"),
    /**
     * 银联
     */
    UNION_PAY(5, "UNION_PAY"),
    /**
     * 现金
     */
    CASH(6, "CASH"),
    /**
     * 支付宝-对个人
     */
    ALIPAY_PERSONAL(7, "ALIPAY_PERSONAL"),
    /**
     * 微信-对个人
     */
    WECHAT_PERSONAL(8, "WECHAT_PERSONAL");

    private final int payWayCode;
    private final String payWayName;
}
