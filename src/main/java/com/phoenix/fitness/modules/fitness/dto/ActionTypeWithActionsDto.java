package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.ActionEntity;
import com.phoenix.fitness.modules.fitness.entity.ActionTypeEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据动作分类查询某个动作分类下面所有的动作
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class ActionTypeWithActionsDto extends ActionTypeEntity {
	/**
	 * 按照动作类型分类
	 * 1个动作类型查询出所有的动作
	 */
	private List<ActionEntity> actions=new ArrayList<>();
}
