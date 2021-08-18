package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 健身房-教练 关联对中间表
 * n健身房 : n教练
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_gym_coach_relation")
public class GymCoachRelationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long gymCoachRelationId;
	/**
	 * 健身房id
	 */
	private Long gymId;
	/**
	 * 教练id
	 */
	private Long coachId;
	/**
	 * 创建时间
	 * 默认当前时间
	 */
	@JsonIgnore
	private Date createTime;
	/**
	 * 修改时间
	 * 默认当前时间
	 */
	@JsonIgnore
	private Date updateTime;
	/**
	 * 是否删除
	 * 0未删除
	 * 1已删除
	 * 默认0
	 */
	@JsonIgnore
	private Integer deleteFlag;

}
