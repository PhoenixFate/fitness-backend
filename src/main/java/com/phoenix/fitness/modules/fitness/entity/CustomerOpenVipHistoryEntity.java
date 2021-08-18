package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_customer_open_vip_history")
public class CustomerOpenVipHistoryEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerOpenVipHistoryId;
    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 教练id
     */
    private Long coachId;
    /**
     * 操作日期
     */
    private Date operationDate;
    /**
     * 手动MANUAL
     * 课程赠送CLASS_SEND
     * 购买会员卡BUY_VIP_CARD
     */
    private String openVipType;
    /**
     * 订单编码
     */
    private String orderNumber;
}
