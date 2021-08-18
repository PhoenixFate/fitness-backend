package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 销卡销卡的发起客户端
 */
@Getter
@AllArgsConstructor
public enum ClientEnum {
    /**
     * 后台管理
     */
    ADMIN(1, "ADMIN"),
    /**
     * pad端
     */
    PAD(2, "PAD"),
    /**
     * 小程序
     */
    MINI_PROGRAM(3, "MINI_PROGRAM");

    private final int clientCode;
    private final String clientName;
}
