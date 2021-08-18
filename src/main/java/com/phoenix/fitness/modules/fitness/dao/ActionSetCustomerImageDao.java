package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.ActionSetCustomerImageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动作组中给顾客拍的照片
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface ActionSetCustomerImageDao extends BaseMapper<ActionSetCustomerImageEntity> {

}
