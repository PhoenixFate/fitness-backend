package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.entity.ActionExampleEntity;

import java.util.Map;

/**
 * 
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
public interface ActionExampleService extends IService<ActionExampleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

