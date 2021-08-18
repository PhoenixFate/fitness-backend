package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.entity.TargetEntity;
import lombok.Data;

import java.util.List;

/**
 * 目标dto，带目标分类, 带关联的教练列表
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class TargetWithAllDto extends TargetEntity {
	/**
	 * 目标关联的教练列表
	 */
	private List<CoachEntity> coaches;
}
