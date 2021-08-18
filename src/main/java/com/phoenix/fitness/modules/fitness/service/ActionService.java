package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.ActionTypeWithActionsDto;
import com.phoenix.fitness.modules.fitness.dto.ActionWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.ActionEntity;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 动作service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
public interface ActionService extends IService<ActionEntity> {

    PageUtils queryPage(Map<String, Object> params, UserEntity user);

    List<ActionTypeWithActionsDto> getActionsWithType(Map<String, Object> params,UserEntity user);

    Boolean removeByDeleteFlag(Long id);

    ActionWithAllDto getDetail(Long id);

    Boolean save(ActionWithAllDto action);

    Boolean updateById(ActionWithAllDto action);
}

