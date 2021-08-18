package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 订单
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_order")
public class OrderEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long orderId;
	/**
	 * 订单编号
	 */
	private String orderNumber;
	/**
	 * 订单中文描述
	 */
	private String orderDescription;
	/**
	 * 顾客id
	 */
	private Long customerId;
	/**
	 * 教练id
	 */
	private Long coachId;
	/**
	 * 总价
	 */
	private String totalPrice;
	/**
	 * 订单类型
	 * OPEN_VIP 开通会员卡
	 * OPEN_CLASS_BY_TOTAL 根据课程单价*总价开通私教课
	 * OPEN_CLASS_BY_TRAINING_PLAN 根据训练计划套餐费用开通私教课
	 */
	private String orderType;
	/**
	 * 支付时间
	 */
	private Date payTime;
	/**
	 * 支付金额
	 */
	private String payMoney;
	/**
	 * 退款时间
	 */
	private Date refundTime;
	/**
	 * 退款金额
	 */
	private String refundMoney;
	/**
	 * 订单状态
	 */
	private String orderStatus;
	/**
	 * 支付方式
	 */
	private String payWay;
}
