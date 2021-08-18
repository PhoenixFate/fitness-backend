package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 目标
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-12-14 17:07:42
 */
@Data
@TableName("tb_target")
public class TargetEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long targetId;
	/**
	 * 目标名称
	 */
	@NotBlank(message = "目标名称不能为空")
	@Size(max = 40,message = "目标名称长度1-40")
	private String targetName;
	/**
	 * 目标描述
	 */
	@Size(max = 512,message = "目标描述长度1-512")
	private String targetDescription;
	/**
	 * 目标分类
	 */
	@NotBlank(message = "目标分类不能为空")
	private String targetType;
	/**
	 * 具体的指标
	 */
	@NotNull(message = "目标具体的指标不能为空")
	private Double detailNumber;
	/**
	 * 目标阶段类型
	 * 日计划 DAT_TARGET
	 * 周计划 WEEK_TARGET
	 * 月计划 MONTH_TARGET
	 * 特殊计划 SPECIAL_TARGET
	 */
	@NotBlank(message = "目标阶段类型不能为空")
	private String targetPeriodType;
	/**
	 * 自定义的开始时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date startDate;
	/**
	 * 自定义的结束时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date endDate;
}
