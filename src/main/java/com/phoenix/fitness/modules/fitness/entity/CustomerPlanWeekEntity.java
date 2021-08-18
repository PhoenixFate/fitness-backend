package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithClassInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * 顾客训练第几周
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_customer_plan_week")
public class CustomerPlanWeekEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long customerPlanWeekId;
	/**
	 * 第几周排序
	 */
	private Integer weekIndex;
	/**
	 * 关联的顾客训练计划id
	 */
	private Long customerPlanId;
	/**
	 * 上级阶段id
	 */
	private Long customerPlanPeriodId;

	@TableField(exist = false)
	private List<CustomerPlanDayWithClassInfoDto> customerPlanDays;
}
