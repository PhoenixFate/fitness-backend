package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 动作关联的设备表
 * 1个动作 多个设备
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_action_equipment")
public class ActionEquipmentEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@TableId
	private Long actionEquipmentRelationId;
	/**
	 * 动作id
	 */
	private Long actionId;
	/**
	 * 设备id
	 */
	private Long equipmentId;
}
