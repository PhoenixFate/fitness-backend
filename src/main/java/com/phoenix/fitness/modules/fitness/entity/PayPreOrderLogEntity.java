package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 支付预下单请求日志
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_pay_pre_order_log")
public class PayPreOrderLogEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 支付宝预下单日志id
	 */
	@TableId
	private Long payPreOrderLogId;
	/**
	 * 顾客id
	 */
	private Long customerId;
	/**
	 * 教练id
	 */
	private Long coachId;
	/**
	 * 请求参数
	 */
	private String requestParams;
	/**
	 * 返回结果
	 */
	private String responseResult;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 支付类型
	 */
	private String payType;
}
