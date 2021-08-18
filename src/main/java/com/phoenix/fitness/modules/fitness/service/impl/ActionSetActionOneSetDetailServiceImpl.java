package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.ActionSetActionOneSetDetailDao;
import com.phoenix.fitness.modules.fitness.entity.ActionSetCustomerOneSetDetailEntity;
import com.phoenix.fitness.modules.fitness.service.ActionSetActionOneSetDetailService;

@Service("actionSetActionOneSetDetailService")
public class ActionSetActionOneSetDetailServiceImpl extends ServiceImpl<ActionSetActionOneSetDetailDao, ActionSetCustomerOneSetDetailEntity> implements ActionSetActionOneSetDetailService {

}