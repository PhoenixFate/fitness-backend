package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.ActionSetActionOneSetDao;
import com.phoenix.fitness.modules.fitness.entity.ActionSetCustomerOneSetEntity;
import com.phoenix.fitness.modules.fitness.service.ActionSetActionOneSetService;

@Service("actionSetActionOneSetService")
public class ActionSetActionOneSetServiceImpl extends ServiceImpl<ActionSetActionOneSetDao, ActionSetCustomerOneSetEntity> implements ActionSetActionOneSetService {

}