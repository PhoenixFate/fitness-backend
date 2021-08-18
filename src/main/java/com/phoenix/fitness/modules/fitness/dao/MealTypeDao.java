package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.entity.MealTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 餐食类型
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface MealTypeDao extends BaseMapper<MealTypeEntity> {

    Integer deleteByDeleteFlag(Long id);
}
