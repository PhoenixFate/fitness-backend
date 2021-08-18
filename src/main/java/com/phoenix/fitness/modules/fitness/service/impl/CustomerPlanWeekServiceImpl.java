package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.CustomerPlanWeekDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanWeekEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanWeekService;


@Service("customerPlanWeekService")
public class CustomerPlanWeekServiceImpl extends ServiceImpl<CustomerPlanWeekDao, CustomerPlanWeekEntity> implements CustomerPlanWeekService {

}