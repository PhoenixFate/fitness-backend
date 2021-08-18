package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.ActionSetActionInCustomerEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动作组中的动作(顾客训练中的实例)
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface ActionSetActionInCustomerDao extends BaseMapper<ActionSetActionInCustomerEntity> {

}
