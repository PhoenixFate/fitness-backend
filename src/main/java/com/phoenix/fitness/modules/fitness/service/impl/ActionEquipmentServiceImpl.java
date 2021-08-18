package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.ActionEquipmentDao;
import com.phoenix.fitness.modules.fitness.entity.ActionEquipmentEntity;
import com.phoenix.fitness.modules.fitness.service.ActionEquipmentService;


@Service("actionEquipmentService")
public class ActionEquipmentServiceImpl extends ServiceImpl<ActionEquipmentDao, ActionEquipmentEntity> implements ActionEquipmentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActionEquipmentEntity> page = this.page(
                new Query<ActionEquipmentEntity>().getPage(params),
                new QueryWrapper<ActionEquipmentEntity>()
        );

        return new PageUtils(page);
    }

}