package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.DietPlanWithItemsDto;
import com.phoenix.fitness.modules.fitness.entity.DietPlanEntity;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.Map;

/**
 * 饮食计划service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface DietPlanService extends IService<DietPlanEntity> {

    PageUtils queryPage(Map<String, Object> params, UserEntity user);

    void removeByDeleteFlag(Long id);

    DietPlanWithItemsDto getDetail(Long id);

    boolean save(DietPlanWithItemsDto dietPlan);

    boolean updateById(DietPlanWithItemsDto dietPlan);
}

