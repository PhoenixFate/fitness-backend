package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.ActionSetActionDao;
import com.phoenix.fitness.modules.fitness.entity.ActionSetActionEntity;
import com.phoenix.fitness.modules.fitness.service.ActionSetActionService;

@Service("actionSetActionService")
public class ActionSetActionServiceImpl extends ServiceImpl<ActionSetActionDao, ActionSetActionEntity> implements ActionSetActionService {


}