package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 动作组类别
 */
@Getter
@AllArgsConstructor
public enum ActionSetTypeEnum {
    /**
     * 普通组
     */
    COMMON_SET(1,"COMMON_SET"),
    /**
     * 超级组
     */
    SUPER_SET(2,"SUPER_SET"),
    /**
     * 递减组
     */
    REDUCE_SET(3, "REDUCE_SET");

    private final int typeCode;
    private final String statusName;
}
