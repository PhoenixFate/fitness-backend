package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_customer_charge_history")
public class CustomerChargeHistoryEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerChargeHistoryId;
    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 教练id
     */
    private Long coachId;
    /**
     * 充值的金额
     */
    private String money;
    /**
     * 充值日期
     */
    private Date chargeDate;
    /**
     * 充值类型
     * OPEN_VIP、
     * OPEN_CLASS_BY_TOTAL、
     * OPEN_CLASS_BY_TRAINING_PLAN
     */
    private String chargeType;
    /**
     * 订单编码
     */
    private String orderNumber;

}
