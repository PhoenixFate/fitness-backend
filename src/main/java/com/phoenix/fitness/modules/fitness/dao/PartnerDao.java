package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.dto.PartnerWithGymsDto;
import com.phoenix.fitness.modules.fitness.entity.PartnerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 合作伙伴DAO
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface PartnerDao extends BaseMapper<PartnerEntity> {

    Integer deleteByDeleteFlag(Long id);

    PartnerWithGymsDto getPartnerWithGyms(Long id);
}
