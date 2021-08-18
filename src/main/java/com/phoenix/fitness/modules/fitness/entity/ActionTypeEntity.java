package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 动作类型
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_action_type")
public class ActionTypeEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@TableId
	private Long actionTypeId;
	/**
	 * 动作类型名称
	 */
	@NotBlank(message="动作分类名称不能为空")
	@Size(max = 40,message = "动作分类名称1-40")
	private String actionTypeName;
	/**
	 * 动作类型图标
	 */
	@TableField(fill = FieldFill.UPDATE)
	@Size(max = 512,message = "动作分类图标1-512")
	private String actionTypeImageUrl;

}
