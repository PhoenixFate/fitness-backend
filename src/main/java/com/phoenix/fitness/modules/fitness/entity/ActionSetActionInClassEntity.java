package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 动作组中的动作
 * 1动作组 : n动作
 * 课程中的实例
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_action_set_action_in_class")
public class ActionSetActionInClassEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@TableId
	private Long actionSetActionInClassId;
	/**
	 * 关联的动作id
	 */
	@NotNull(message = "动作id不能为空")
	private Long actionId;
	/**
	 * 上级动作组id
	 */
	@JsonIgnore
	private Long actionSetClassId;
	/**
     * 训练项id
	 */
	@JsonIgnore
	private Long exerciseId;
	/**
	 * 课程id
	 */
	@JsonIgnore
	private Long classId;
	/**
	 * 每组次数
	 */
	private Integer count;
	/**
	 * 重量
	 */
	private Double weight;
	/**
	 * 训练时长(针对训练单位为秒的)
	 */
	private Integer time;
	/**
	 * 排序
	 */
	private Integer sort;
	@TableField(exist = false)
	private String actionName;

	@TableField(exist = false)
	private Integer containWeight;

	@TableField(exist = false)
	private String unit;

	@TableField(exist = false)
	private String actionVideoUrl;
}
