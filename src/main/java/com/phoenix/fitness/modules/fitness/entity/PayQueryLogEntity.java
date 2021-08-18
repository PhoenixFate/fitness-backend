package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 支付查询日志
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_pay_query_log")
public class PayQueryLogEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 支付宝查询日志id
	 */
	@TableId
	private Long payQueryLogId;
	/**
	 * 订单号
	 */
	private String orderNumber;
	/**
	 * 查询结果
	 */
	private String queryResult;
	/**
	 * 查询时间
	 */
	private Date queryTime;
	/**
	 * 支付类型
	 */
	private String payType;
}
