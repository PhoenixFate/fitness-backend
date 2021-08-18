package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.ActionEntity;
import com.phoenix.fitness.modules.fitness.entity.ActionTypeEntity;
import com.phoenix.fitness.modules.fitness.entity.EquipmentEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 动作dto，全含动作的所有要素
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class ActionWithAllDto extends ActionEntity {
	/**
	 * 动作分类
	 */
	@NotNull(message="动作分类不能为空")
	private ActionTypeEntity actionType;
	/**
	 * 动作关联的动作示范
	 * 1个动作 ： n动作示范
	 */
	private List<String> actionExamples=new ArrayList<>();
	/**
	 * 动作关联的设备
	 * 1个动作 ： n个设备
	 */
	private List<EquipmentEntity> equipments=new ArrayList<>();
}
