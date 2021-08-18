package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.sql.Time;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 餐食类型
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_meal_type")
public class MealTypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long mealTypeId;
	/**
	 * 餐食分类名称
	 * 早餐
	 * 早餐加餐
	 * 午餐
	 * 午餐加餐
	 * 晚餐
	 */
	@NotBlank(message="餐食分类名称不能为空")
	@Size(max = 50,message = "餐食分类名称长度1-50")
	private String mealTypeName;
	/**
	 * 餐食分类值
	 * 早餐     BREAKFAST
	 * 早餐加餐  BREAKFAST_MORE
	 * 午餐     LAUNCH
	 * 午餐加餐  LAUNCH_MORE
	 * 晚餐     DINNER
	 */
	@NotBlank(message="餐食分类值不能为空")
	private String mealTypeValue;
	/**
	 * 餐食开始时间
	 */
	@NotNull(message = "餐食开始时间不能为空")
	private Time startTime;
	/**
	 * 餐食结束时间
	 */
	@NotNull(message = "餐食结束时间不能为空")
	private Time endTime;
	/**
	 * 排序
	 */
	private Integer sort;

}
