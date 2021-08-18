package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 顾客权益表
 * 只有支付过了才进入权益
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_customer_contract")
public class CustomerContractEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerContractId;
    /**
     * 顾客id
     */
    private Long customerId;
    @TableField(exist = false)
    private String customerName;
    @TableField(exist = false)
    private String mobile;
    /**
     * 合同类型
     */
    private String contractType;
    /**
     * 会员卡大分类
     */
    private String vipCardBigType;
    /**
     * 私教分类
     */
    private String trainingType;
    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 合同编号
     */
    private String contractNumber;
    /**
     * 原始总价
     */
    private String totalMoney;
    /**
     * 支付总金额
     */
    private String payTotalMoney;
    /**
     * 税后支付总金额（包括扣除抵用券的费用）
     */
    private String afterTaxPayMoney;
    /**
     * 支付金额
     */
    private String payMoney;
    /**
     * 支付金额2
     */
    private String payMoney2;
    /**
     * 剩余金额
     */
    private String leftMoney;
    /**
     * 合同状态
     */
    private String contractStatus;
    /**
     * 所有权益
     */
    private String totalRights;
    /**
     * 剩余权益
     */
    private String leftRights;
    /**
     * 激活时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date activeTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
    /**
     * 销售员姓名
     */
    private String salesmanName;
    /**
     * 销售员id
     */
    private Long salesmanId;
    /**
     * 上课教练id
     */
    private Long coachId;
    /**
     * 上课教练姓名
     */
    private String coachName;
    /**
     * 操作时间
     */
    private Date operationTime;
    /**
     * 付款时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date payTime;
    /**
     * 是否有线下合同
     */
    private String offlineContract;
    /**
     * 会员卡
     */
    private Long vipCardId;
    @TableField(exist = false)
    private String vipCardName;
    /**
     * 训练计划id
     */
    private Long trainingPlanId;
    @TableField(exist = false)
    private String trainingPlanName;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 赠送天数
     */
    private Integer freeDays;
    /**
     * 单节课价格
     */
    private String oneClassMoney;
    /**
     * 是否是老合同,
     * 默认为0，新合同
     */
    private Integer isOld;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 支付方式2
     */
    private String payType2;
    /**
     * 抵用券金额
     */
    private Double couponMoney;
    /**
     * 退款金额
     */
    private String refundMoney;
    /**
     * 退款支付方式
     */
    private String refundPayType;
    /**
     * 退款日期
     */
    private Date refundDate;
    /**
     * 退款操作时间
     */
    private Date refundOperationTime;
}
