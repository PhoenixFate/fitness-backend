package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 目标阶段类型
 * 日计划 DAT_TARGET
 * 周计划 WEEK_TARGET
 * 月计划 MONTH_TARGET
 * 特殊计划 SPECIAL_TARGET
 */
@Getter
@AllArgsConstructor
public enum TargetPeriodTypeEnum {

    DAT_TARGET(1,"DAT_TARGET"),
    WEEK_TARGET(2,"WEEK_TARGET"),
    MONTH_TARGET(3,"MONTH_TARGET"),
    SPECIAL_TARGET(10,"SPECIAL_TARGET")
    ;
    private final int typeCode;
    private final String statusName;

}
