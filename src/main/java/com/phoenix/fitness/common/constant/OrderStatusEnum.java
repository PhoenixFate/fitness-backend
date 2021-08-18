package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单状态
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    /**
     * 订单初始化，未生成二维码
     */
    INITIAL(1,"INITIAL"),
    /**
     * 已生成二维码，未支付
     */
    UNPAID(2,"UNPAID"),
    /**
     * 已支付
     */
    PAID(3, "PAID"),
    /**
     * 已退款
     */
    REFUNDED(4, "REFUNDED"),
    /**
     * 已完成
     */
    FINISHED(5, "FINISHED");


    private final int statusCode;
    private final String statusName;
}
