package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 支付回调日志
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_pay_notify_log")
public class PayNotifyLogEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 支付宝通知日志id
	 */
	@TableId
	private Long payNotifyLogId;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 支付宝通知的内容
	 */
	private String notifyContent;
	/**
	 * 支付宝通知的时间
	 */
	private Date notifyTime;
	/**
	 * 支付类型
	 */
	private String payType;
}
