package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.GymWithCoachesDto;
import com.phoenix.fitness.modules.fitness.dto.GymWithPartnerDto;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 健身房DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface GymDao extends BaseMapper<GymEntity> {

  Integer deleteByDeleteFlag(Long id);

  IPage<GymWithPartnerDto> selectGymWithPartner(IPage<GymWithPartnerDto> pageParams,
                                                @Param("gymName") String gymName);

  GymWithCoachesDto selectGymWithCoaches(Long id);
}
