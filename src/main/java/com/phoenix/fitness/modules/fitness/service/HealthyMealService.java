package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.HealthyMealWithItemsDto;
import com.phoenix.fitness.modules.fitness.entity.HealthyMealEntity;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 健康餐service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface HealthyMealService extends IService<HealthyMealEntity> {

    PageUtils queryPage(Map<String, Object> params, UserEntity user);

    List<HealthyMealWithItemsDto> listWithDetail(UserEntity user);

    void removeByDeleteFlag(Long id);

    HealthyMealWithItemsDto getDetail(Long id);

    boolean save(HealthyMealWithItemsDto healthyMeal);

    boolean updateById(HealthyMealWithItemsDto healthyMeal);
}

