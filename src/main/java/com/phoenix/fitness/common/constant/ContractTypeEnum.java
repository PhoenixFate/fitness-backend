package com.phoenix.fitness.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 会员合同分类
 */
@Getter
@AllArgsConstructor
public enum ContractTypeEnum {
    /**
     * 会员卡
     */
    VIP_CARD_CONTRACT(1, "VIP_CARD_CONTRACT"),
    /**
     * 私教课
     */
    TRAINING_CONTRACT(2, "TRAINING_CONTRACT"),
    /**
     * 转卡费
     */
    TRANSFER_CARD(3, "TRANSFER_CARD"),
    /**
     * 其他销售
     */
    OTHER_SALES(4, "OTHER_SALES"),
    /**
     * 团购补差价
     */
    GROUP_PURCHASE_PRICE(5, "GROUP_PURCHASE_PRICE"),
    /**
     * 退款合同
     */
    REFUND(6, "REFUND");


    private final int contractTypeCode;
    private final String contractTypeName;
}
