package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.dto.DietPlanWithItemsDto;
import com.phoenix.fitness.modules.fitness.entity.DietPlanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 饮食计划DAO
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface DietPlanDao extends BaseMapper<DietPlanEntity> {

    DietPlanWithItemsDto selectDietPlanWithDetail(Long id);

    Integer deleteByDeleteFlag(Long id);
}
