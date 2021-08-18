package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 饮食计划entity
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_diet_plan")
public class DietPlanEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long dietPlanId;
	/**
	 * 饮食计划名称
	 */
	@NotBlank(message = "饮食计划名称不能为空")
	@Size(max = 40,message = "饮食计划名称长度1-40")
	private String dietPlanName;
	/**
	 * 饮食计划介绍
	 */
	@Size(max = 512,message = "饮食计划介绍长度1-40")
	private String dietPlanDescription;
}
