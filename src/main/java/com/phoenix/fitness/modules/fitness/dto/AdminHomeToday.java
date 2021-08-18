package com.phoenix.fitness.modules.fitness.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class AdminHomeToday implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 新增顾客数量
     */
    private Integer customerCount;
    /**
     * 新增会员数量
     */
    private Integer vipCustomerCount;
    /**
     * 新办健身卡数量
     */
    private Integer cardSaleCount;
    /**
     * 销售金额
     */
    private String chargeMoney;
}
