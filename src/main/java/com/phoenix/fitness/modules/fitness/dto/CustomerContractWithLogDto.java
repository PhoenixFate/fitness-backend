package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerReturnVisitEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerSureClassLog;
import lombok.Data;

import java.util.List;

@Data
public class CustomerContractWithLogDto extends CustomerContractEntity {

    /**
     * 到账流水
     */
    private String inAccountMoney;
    /**
     * 券后收入
     */
    private String afterCouponInAccountMoney;
    /**
     * 税后券后的实际到账流水
     */
    private String afterTaxInAccountMoney;
    /**
     * 实际收入（搜索时间段内段收入）
     * 毛利润
     */
    private String realIncome;
    /**
     * 税后毛利润
     */
    private String afterTaxRealIncome;
    /**
     * 实际使用权益（搜索时间段内段使用权益）
     */
    private String realUsedCount;
    /**
     * 实际总的使用权益
     */
    private String totalUsedCount;
    /**
     * 总的使用金额
     */
    private String totalUsedMoney;

    private String activeTimeStr;

    private String endTimeStr;

    private String payTimeStr;

    private String operationTimeStr;

    private CustomerEntity customer;

    private List<CustomerSureClassLog> customerSureClassLogList;

    private List<CustomerReturnVisitEntity> customerReturnVisits;
}
