package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import com.phoenix.fitness.modules.fitness.entity.PartnerEntity;
import lombok.Data;

import java.util.List;

/**
 * 合作伙伴dto，包含名下所有的健身房
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class PartnerWithGymsDto extends PartnerEntity {
	/**
	 * 名下所有的健身房
	 */
	private List<GymEntity> gyms;
}
