package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 训练计划中的某一天的训练
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Data
@TableName("tb_training_period_day")
public class TrainingPeriodDayEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long trainingPeriodDayId;
	/**
	 * 关联的阶段
	 */
	@JsonIgnore
	private Long trainingPeriodId;
	/**
	 * 关联的训练计划
	 */
	@JsonIgnore
	private Long trainingPlanId;
	/**
	 * 关联的课程
	 */
	@TableField(fill = FieldFill.UPDATE)
	private Long classId;
	/**
	 * 日期索引（排序）
	 */
	private Integer dayIndex;
	/**
	 * 是否休息
	 */
	@NotNull(message = "是否休息不能为空")
	private Integer isRest;

}
