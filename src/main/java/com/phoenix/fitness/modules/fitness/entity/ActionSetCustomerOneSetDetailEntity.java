package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;

/**
 * 顾客训练中的每组训练详情数据数据
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Data
@TableName("tb_action_set_customer_one_set_detail")
public class ActionSetCustomerOneSetDetailEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long oneSetDetailId;
	/**
	 * 上级每组id
	 */
	@JsonIgnore
	private Long oneSetId;
	/**
	 * 排序
	 */
	private Integer detailIndex;
	/**
	 * 标准每组次数
	 */
	private Integer standardCount;
	/**
	 * 实际每组次数
	 */
	private Integer realCount;
	/**
	 * 标准重量
	 */
	private Double standardWeight;
	/**
	 * 实际重量
	 */
	private Double realWeight;
	/**
	 * 标准训练时长(针对训练单位为秒的)
	 */
	private Integer standardTime;
	/**
	 * 实际训练时长(针对训练单位为秒的)
	 */
	private Integer realTime;
	/**
	 * 标准训练米数(针对训练单位为米)
	 */
	private Integer standardMeter;
	/**
	 * 实际训练米数(针对训练单位为米)
	 */
	private Integer realMeter;
	/**
	 * 关联的顾客训练id
	 */
	private Long customerPlanDayId;
	/**
	 * 是否完成
	 */
	private Integer isComplete;
	/**
	 * 关联的动作id
	 */
	private Long actionId;
	/**
	 * 动作
	 */
	@TableField(exist = false)
	private ActionEntity action;
}
