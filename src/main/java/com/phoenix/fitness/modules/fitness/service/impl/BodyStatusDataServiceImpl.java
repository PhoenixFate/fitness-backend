package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.BodyStatusDataDao;
import com.phoenix.fitness.modules.fitness.entity.BodyStatusDataEntity;
import com.phoenix.fitness.modules.fitness.service.BodyStatusDataService;


@Service("bodyStatusDataService")
public class BodyStatusDataServiceImpl extends ServiceImpl<BodyStatusDataDao, BodyStatusDataEntity> implements BodyStatusDataService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BodyStatusDataEntity> page = this.page(
                new Query<BodyStatusDataEntity>().getPage(params),
                new QueryWrapper<BodyStatusDataEntity>()
        );

        return new PageUtils(page);
    }

}