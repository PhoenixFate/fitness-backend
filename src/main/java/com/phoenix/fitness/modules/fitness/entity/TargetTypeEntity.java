package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 目标类型
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-12-14 17:07:42
 */
@Data
@TableName("tb_target_type")
public class TargetTypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long targetTypeId;
	/**
	 * 目标类型名称
	 */
	@NotBlank(message="目标类型名称不能为空")
	@Size(max = 40,message = "目标类型名称长度1-40")
	private String targetTypeName;

}
