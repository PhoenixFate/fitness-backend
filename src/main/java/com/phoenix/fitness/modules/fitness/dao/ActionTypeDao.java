package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.dto.ActionTypeWithActionsDto;
import com.phoenix.fitness.modules.fitness.entity.ActionTypeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;

/**
 * 动作分类DAO
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface ActionTypeDao extends BaseMapper<ActionTypeEntity> {

    List<ActionTypeWithActionsDto> selectActionTypeWithAction(String actionName, UserEntity user);

    Integer deleteByDeleteFlag(Long ids);
}
