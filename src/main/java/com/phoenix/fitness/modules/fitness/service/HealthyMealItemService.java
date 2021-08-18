package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.entity.HealthyMealItemEntity;

import java.util.Map;

/**
 * 
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface HealthyMealItemService extends IService<HealthyMealItemEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

