package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.entity.ActionSetEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface ActionSetDao extends BaseMapper<ActionSetEntity> {

    // ActionSetEntity selectActionSetWithMovement(Long id);
}
