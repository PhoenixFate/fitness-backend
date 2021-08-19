package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 训练方向
 */
@Getter
@AllArgsConstructor
public enum ExerciseDirectionEnum {
    /**
     * 减脂
     */
    REDUCE_FAT(1,"REDUCE_FAT"),
    /**
     * 增肌
     */
    INCREASE_MUSCLE(2,"INCREASE_MUSCLE"),
    /**
     * 力量
     */
    STRENGTH(3, "STRENGTH"),
    /**
     * 恢复
     */
    RECOVERY(4, "RECOVERY");

    private final int typeCode;
    private final String statusName;
}
