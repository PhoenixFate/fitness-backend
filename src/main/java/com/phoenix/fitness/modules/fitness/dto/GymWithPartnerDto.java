package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import com.phoenix.fitness.modules.fitness.entity.PartnerEntity;
import lombok.Data;

/**
 * 健身房dto，带合作伙伴
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class GymWithPartnerDto extends GymEntity {
	/**
     * 合作伙伴
	 */
	private PartnerEntity partner;
}
