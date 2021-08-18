package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.ActionExampleDao;
import com.phoenix.fitness.modules.fitness.entity.ActionExampleEntity;
import com.phoenix.fitness.modules.fitness.service.ActionExampleService;


@Service("actionExampleService")
public class ActionExampleServiceImpl extends ServiceImpl<ActionExampleDao, ActionExampleEntity> implements ActionExampleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActionExampleEntity> page = this.page(
                new Query<ActionExampleEntity>().getPage(params),
                new QueryWrapper<ActionExampleEntity>()
        );

        return new PageUtils(page);
    }

}