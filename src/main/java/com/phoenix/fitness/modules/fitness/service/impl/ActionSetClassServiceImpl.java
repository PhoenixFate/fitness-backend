package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.ActionSetClassDao;
import com.phoenix.fitness.modules.fitness.entity.ActionSetClassEntity;
import com.phoenix.fitness.modules.fitness.service.ActionSetClassService;


@Service("actionSetClassService")
public class ActionSetClassServiceImpl extends ServiceImpl<ActionSetClassDao, ActionSetClassEntity> implements ActionSetClassService {


}