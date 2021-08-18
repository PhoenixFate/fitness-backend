package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.DietPlanItemDao;
import com.phoenix.fitness.modules.fitness.entity.DietPlanItemEntity;
import com.phoenix.fitness.modules.fitness.service.DietPlanItemService;


@Service("dietPlanItemService")
public class DietPlanItemServiceImpl extends ServiceImpl<DietPlanItemDao, DietPlanItemEntity> implements DietPlanItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DietPlanItemEntity> page = this.page(
                new Query<DietPlanItemEntity>().getPage(params),
                new QueryWrapper<DietPlanItemEntity>()
        );

        return new PageUtils(page);
    }

}