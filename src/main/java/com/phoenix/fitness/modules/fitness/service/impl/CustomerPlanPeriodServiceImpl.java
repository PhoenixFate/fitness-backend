package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.CustomerPlanPeriodDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanPeriodEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanPeriodService;

import java.util.Map;

@Service("customerPlanPeriodService")
public class CustomerPlanPeriodServiceImpl extends ServiceImpl<CustomerPlanPeriodDao, CustomerPlanPeriodEntity> implements CustomerPlanPeriodService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CustomerPlanPeriodEntity> page = this.page(
                new Query<CustomerPlanPeriodEntity>().getPage(params),
                new QueryWrapper<CustomerPlanPeriodEntity>()
        );
        return new PageUtils(page);
    }

}