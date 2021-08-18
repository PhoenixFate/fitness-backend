package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.TargetWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.TargetEntity;
import org.apache.ibatis.annotations.Mapper;
import com.phoenix.fitness.modules.fitness.vo.TargetSearchForm;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-12-14 17:07:42
 */
@Mapper
public interface TargetDao extends BaseMapper<TargetEntity> {

    IPage<TargetEntity> selectTargetWithTargetType(IPage<TargetEntity> page, TargetSearchForm searchForm);

    Integer deleteByDeleteFlag(Long id);

    TargetWithAllDto selectTargetWithAll(Long id);
}
