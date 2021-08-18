package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 目标和教练中间表
 * n目标 : n教练
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-12-14 17:07:42
 */
@Data
@TableName("tb_target_coach_relation")
public class TargetCoachRelationEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long targetCoachRelationId;
	/**
	 * 教练id
	 */
	private Long coachId;
	/**
	 * 目标id
	 */
	private Long targetId;
	/**
	 * 可以针对某些教练设定特殊值
	 */
	private Double specialDetailNumber;
}
