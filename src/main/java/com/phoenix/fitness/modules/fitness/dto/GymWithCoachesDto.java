package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import com.phoenix.fitness.modules.fitness.entity.PartnerEntity;
import lombok.Data;

import java.util.List;

/**
 * 健身房dto，包含下面所有的教练
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class GymWithCoachesDto extends GymEntity {
	/**
	 * 合作伙伴
	 */
	private PartnerEntity partner;
	/**
     * 下面所有的教练
	 */
	private List<CoachEntity> coaches;
}
