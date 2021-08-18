package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 动作关联的动作示范
 * 1个动作 多个动作示范
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_action_example")
public class ActionExampleEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */
	@TableId
	private Long actionExampleRelationId;
	/**
	 * 动作示范图片
	 */
	private String actionExampleImage;
	/**
	 * 动作id
	 */
	private Long actionId;
}
