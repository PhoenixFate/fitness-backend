package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 私教课分类
 */
@Getter
@AllArgsConstructor
public enum TrainingTypeEnum {
    /**
     * 体验课
     */
    EXPERIENCE_TRAINING(1, "EXPERIENCE_TRAINING"),
    /**
     * 包月
     */
    MONTH_TRAINING(2, "MONTH_TRAINING"),
    /**
     * 多节课的私教
     */
    CLASS_TRAINING(3, "CLASS_TRAINING");

    private final int trainingTypeCode;
    private final String trainingTypeName;
}
