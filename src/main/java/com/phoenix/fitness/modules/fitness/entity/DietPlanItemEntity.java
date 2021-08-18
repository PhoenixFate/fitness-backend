package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;

/**
 * 饮食计划子项
 * 1饮食计划 : n饮食计划子项
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_diet_plan_item")
public class DietPlanItemEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long dietPlanItemId;
	/**
	 * 子项排序
	 */
	private Integer sort;
	/**
	 * 热量
	 */
	private Double hot;
	/**
	 * 碳水
	 */
	private Double carbon;
	/**
	 * 脂肪
	 */
	private Double fat;
	/**
	 * 蛋白质
	 */
	private Double protein;
	/**
	 * 关联的餐食类型
	 */
	@JsonIgnore
	private Long mealTypeId;
	/**
	 * 关联的饮食计划
	 */
	@JsonIgnore
	private Long dietPlanId;
	/**
	 * 关联的推荐健康餐
	 */
	@JsonIgnore
	private Long healthyMealId;

}
