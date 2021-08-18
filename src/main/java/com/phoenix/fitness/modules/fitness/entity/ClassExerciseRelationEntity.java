package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 课程、训练项关联
 * 1课程 ： n训练项
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_class_exercise_relation")
public class ClassExerciseRelationEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 关联id
	 */
	@TableId
	private Long class_exercise_relation_id;
	/**
	 * 训练项id
	 */
	private Long exerciseId;
	/**
	 * 课程id
	 */
	private Long classId;
	/**
	 * 排序
	 */
	private Integer sort;
}
