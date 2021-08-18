package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 动作训练单位
 */
@Getter
@AllArgsConstructor
public enum ActionUnitEnum {
    /**
     * 秒
     */
    SECOND(1, "SECOND"),
    /**
     * 组
     */
    SET(2, "SET");

    private final int typeCode;
    private final String actionUnitName;
}
