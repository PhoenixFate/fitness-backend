package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.BodyTestDataDao;
import com.phoenix.fitness.modules.fitness.entity.BodyTestDataEntity;
import com.phoenix.fitness.modules.fitness.service.BodyTestDataService;


@Service("bodyTestDataService")
public class BodyTestDataServiceImpl extends ServiceImpl<BodyTestDataDao, BodyTestDataEntity> implements BodyTestDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BodyTestDataEntity> page = this.page(
                new Query<BodyTestDataEntity>().getPage(params),
                new QueryWrapper<BodyTestDataEntity>()
        );

        return new PageUtils(page);
    }

}