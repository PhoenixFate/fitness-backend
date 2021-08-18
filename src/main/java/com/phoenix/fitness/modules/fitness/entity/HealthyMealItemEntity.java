package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 健康餐子项
 * 1健康餐 : n健康餐子项
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_healthy_meal_item")
public class HealthyMealItemEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long healthyMealItemId;
	/**
	 * 健康餐id
	 */
	@JsonIgnore
	private Long healthyMealId;
	/**
	 * 健康餐子项名称
	 */
	@NotBlank(message="健康餐封面照片不能为空")
	@Size(max = 256,message = "健康餐封面照片长度1-256")
	private String healthyMealItemName;
	/**
	 * 单个子项重量
	 * 单位：克
	 */
	@NotNull(message = "食材重量不能为空")
	private Double weight;
	/**
	 * 份数
	 */
	@NotNull(message = "份数不能为空")
	private Integer share;
}
