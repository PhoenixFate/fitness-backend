package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_customer_vip_duration")
public class CustomerVipDurationEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerVipDurationId;
    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 帮这个顾客开通vip的教练
     */
    private Long coachId;
    /**
     * vip某阶段开始日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    /**
     * vip某阶段结束日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    /**
     * 会员卡类型
     */
    private String vipCardBigType;
    /**
     * vip有效天数
     */
    private Integer days;
    /**
     * 剩余天数
     */
    private Integer leftDays;
    /**
     * 关联的支付的订单编码
     */
    private String orderNumber;
    /**
     * 会员卡id
     */
    private Long vipCardId;
    /**
     * 会员卡支付的价钱
     */
    private String payMoney;
    /**
     * 可以使用次数
     */
    private Integer totalTimes;
    /**
     * 已经使用次数
     */
    private Integer usedTimes;

    @TableField(exist = false)
    private VipCardEntity vipCard;
}
