package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员合同状态
 */
@Getter
@AllArgsConstructor
public enum ContractStatusEnum {
    /**
     * 预收费
     */
    PREPAYMENT(1, "PREPAYMENT"),
    /**
     * 未开始
     */
    NO_START(2, "NO_START"),
    /**
     * 进行中
     */
    IN_PROGRESS(3, "IN_PROGRESS"),
    /**
     * 已结束
     */
    FINISHED(4, "FINISHED"),
    /**
     * 未使用完，但过期
     */
    EXPIRED(5, "EXPIRED"),
    /**
     * 手动删除
     */
    DELETE_MANUALLY(6, "DELETE_MANUALLY"),
    /**
     * 已退款
     */
    REFUNDED(7, "REFUNDED");


    private final int contractStatusCode;
    private final String contractStatusName;
}
