package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 动作组(课程中动作组的实例)
 * 1节课 : n训练项目 : n动作组
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_action_set_class")
public class ActionSetClassEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
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
     * 普通组 COMMON_SET
     * 超级组 SUPER_SET
     * 递减组 REDUCE_SET
     */
    private String actionSetType;
	/**
     * 组数
	 */
	private Integer setNumber;
    /**
     * 递减组数
	 */
	private Integer reduceSetNumber;
	/**
     * 组建休息时间
	 */
    private Integer restInternal;
    /**
     * 排序
     * 默认生序
     */
    private Integer sort;
}
