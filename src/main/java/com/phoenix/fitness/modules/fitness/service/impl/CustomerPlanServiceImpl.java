package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.CustomerPlanDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanService;


@Service("customerPlanService")
public class CustomerPlanServiceImpl extends ServiceImpl<CustomerPlanDao, CustomerPlanEntity> implements CustomerPlanService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CustomerPlanEntity> page = this.page(
                new Query<CustomerPlanEntity>().getPage(params),
                new QueryWrapper<CustomerPlanEntity>()
        );

        return new PageUtils(page);
    }

}