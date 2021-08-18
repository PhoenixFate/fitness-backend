package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.ActionWithActionTypeDto;
import com.phoenix.fitness.modules.fitness.dto.ActionWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.ActionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.phoenix.fitness.modules.admin.form.ActionSearchForm;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

/**
 * 动作DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface ActionDao extends BaseMapper<ActionEntity> {

    ActionWithAllDto selectActionWithAll(Long id);

    Integer deleteByDeleteFlag(Long id);

    IPage<ActionEntity> selectActionWithActionType(IPage<ActionWithActionTypeDto> pageParams, @Param("searchForm") ActionSearchForm searchForm, @Param("user") UserEntity user);
}
