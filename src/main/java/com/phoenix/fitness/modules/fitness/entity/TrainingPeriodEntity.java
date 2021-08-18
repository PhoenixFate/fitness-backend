package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 训练计划阶段
 * 1训练计划 : n训练计划阶段
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Data
@TableName("tb_training_period")
public class TrainingPeriodEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long trainingPeriodId;
	/**
	 * 训练阶段索引（排序）
	 */
	private Integer periodIndex;
	/**
	 * 阶段名称
	 */
	@NotEmpty(message = "训练阶段名称不能为空")
	@Size(max = 40,message = "训练阶段名称最大长度为40")
	private String periodName;
	/**
	 * 阶段描述
	 */
	@Size(max = 512,message = "训练阶段名称最大长度为512")
	private String periodDescription;
	/**
	 * 关联的训练计划
	 */
	@JsonIgnore
	private Long trainingPlanId;
	/**
	 * 训练天数
	 */
	@NotNull(message = "训练天数不能为空")
	private Integer trainingDays;
	/**
	 * 单次循环天数
	 */
	@NotNull(message = "单次循环天数不能为空")
	private Integer oneCircleDays;
	/**
	 * 循环重复次数
	 */
	@NotNull(message = "循环重复次数不能为空")
	private Integer circleRepeatTimes;

}
