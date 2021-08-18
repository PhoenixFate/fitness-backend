package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 健康餐
 * 1健康餐 : n健康餐子项
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_healthy_meal")
public class HealthyMealEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long healthyMealId;
	/**
	 * 健康餐名称
	 */
	@NotBlank(message="健康餐名称不能为空")
	@Size(max = 50,message = "健康餐名称长度1-50")
	private String healthyMealName;
	/**
	 * 健康餐封面照片
	 */
	@NotBlank(message="健康餐封面照片不能为空")
	@Size(max = 256,message = "健康餐封面照片长度1-256")
	private String healthyMealCoverImage;
	/**
	 * 热量
	 */
	@NotNull(message = "热量不能为空")
	private Double hot;
	/**
	 * 碳水
	 */
	@NotNull(message = "碳水不能为空")
	private Double carbon;
	/**
	 * 脂肪
	 */
	@NotNull(message = "脂肪不能为空")
	private Double fat;
	/**
	 * 蛋白质
	 */
	@NotNull(message = "蛋白质不能为空")
	private Double protein;

}
