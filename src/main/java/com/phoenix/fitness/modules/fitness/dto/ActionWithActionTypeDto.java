package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.ActionEntity;
import com.phoenix.fitness.modules.fitness.entity.ActionTypeEntity;
import lombok.Data;

/**
 * 动作dto，全含动作的所有要素
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class ActionWithActionTypeDto extends ActionEntity {
	/**
	 * 动作分类
	 */
	private ActionTypeEntity actionType;
}
