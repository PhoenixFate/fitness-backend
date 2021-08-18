package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.HealthyMealItemDao;
import com.phoenix.fitness.modules.fitness.entity.HealthyMealItemEntity;
import com.phoenix.fitness.modules.fitness.service.HealthyMealItemService;


@Service("healthyMealItemService")
public class HealthyMealItemServiceImpl extends ServiceImpl<HealthyMealItemDao, HealthyMealItemEntity> implements HealthyMealItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HealthyMealItemEntity> page = this.page(
                new Query<HealthyMealItemEntity>().getPage(params),
                new QueryWrapper<HealthyMealItemEntity>()
        );

        return new PageUtils(page);
    }

}