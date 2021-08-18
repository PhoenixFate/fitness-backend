package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.ActionSetCustomerDao;
import com.phoenix.fitness.modules.fitness.entity.ActionSetCustomerEntity;
import com.phoenix.fitness.modules.fitness.service.ActionSetCustomerService;


@Service("actionSetCustomerService")
public class ActionSetCustomerServiceImpl extends ServiceImpl<ActionSetCustomerDao, ActionSetCustomerEntity> implements ActionSetCustomerService {


}