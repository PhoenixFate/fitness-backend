package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.ActionSetCustomerOneSetEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 顾客训练-每组的训练数据
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Mapper
public interface ActionSetActionOneSetDao extends BaseMapper<ActionSetCustomerOneSetEntity> {
	
}
