package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.ActionSetDao;
import com.phoenix.fitness.modules.fitness.entity.ActionSetEntity;
import com.phoenix.fitness.modules.fitness.service.ActionSetService;


@Service("actionSetService")
public class ActionSetServiceImpl extends ServiceImpl<ActionSetDao, ActionSetEntity> implements ActionSetService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActionSetEntity> page = this.page(
                new Query<ActionSetEntity>().getPage(params),
                new QueryWrapper<ActionSetEntity>()
        );

        return new PageUtils(page);
    }

}