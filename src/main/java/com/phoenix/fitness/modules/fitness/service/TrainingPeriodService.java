package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.entity.TrainingPeriodEntity;

import java.util.Map;

/**
 * 
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
public interface TrainingPeriodService extends IService<TrainingPeriodEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

