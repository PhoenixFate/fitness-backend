package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 顾客状态
 */
@Getter
@AllArgsConstructor
public enum CustomerStatusEnum {
    /**
     * 正常
     */
    COMMON(1, "COMMON"),
    /**
     * 停卡
     */
    STOP_CARD(2, "STOP_CARD"),
    /**
     * 已注销
     */
    CANCELLED(3, "CANCELLED");

    private final int customerStatusCode;
    private final String customerStatusName;
}
