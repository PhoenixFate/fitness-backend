package com.phoenix.fitness.modules.admin.dto;

import lombok.Data;

@Data
public class TotalBillDto {
    /**
     * 总的到账流水金额
     */
    private String totalInAccountMoney;
    /**
     * 总的券后到账流水金额
     */
    private String totalAfterCouponInAccountMoney;
    /**
     * 税后券后到账流水金额
     */
    private String totalAfterTaxInAccountMoney;
    /**
     * 总的实际搜索时间段内段收入
     */
    private String totalRealMoney;
    /**
     * 总的税后毛利润
     */
    private String totalAfterTaxRealMoney;

}
