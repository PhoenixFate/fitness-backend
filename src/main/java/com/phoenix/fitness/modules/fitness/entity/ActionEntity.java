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
 * 标准动作
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_action")
public class ActionEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@TableId
	private Long actionId;
	/**
	 * 动作名称
	 */
	@NotBlank(message="动作名称不能为空")
	@Size(max = 40,message = "动作名称长度1-40")
	private String actionName;
	/**
	 * 动作类型id
	 */
	@JsonIgnore
	private Long actionTypeId;
	/**
	 * 动作描述
	 */
	@Size(max = 512,message = "动作描述长度1-512")
	private String actionDescription;
	/**
	 * 是否包含重量：0不包含；1包含
	 */
	@NotNull(message="是否包含重量不能为空")
	private Integer containWeight;
	/**
	 * 动作训练单位
	 * SECOND 秒
	 * SET 组
	 */
	@NotNull(message="动作训练单位不能为空")
	private String unit;
	/**
	 * 视频地址
	 */
	@Size(max = 512,message = "动作地址长度1-512")
	private String actionVideoUrl;

}
