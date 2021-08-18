package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Data
@TableName("tb_customer_plan_period")
public class CustomerPlanPeriodEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long customerPlanPeriodId;
	/**
	 * 阶段索引（排序）
	 */
	private Integer periodIndex;
	/**
	 * 阶段名称
	 */
	private String periodName;
	/**
	 * 客户计划（客户计划是标准计划的实例）
	 */
	private Long customerPlanId;
	/**
	 * 顾客id
	 */
	private Long customerId;

	@TableField(exist = false)
	private List<CustomerPlanWeekEntity> customerPlanWeeks;
}
