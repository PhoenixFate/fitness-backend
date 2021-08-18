package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.CustomerShareImageDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerShareImageEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerShareImageService;
import java.util.Map;

@Service("customerShareImageService")
public class CustomerShareImageServiceImpl extends ServiceImpl<CustomerShareImageDao, CustomerShareImageEntity> implements CustomerShareImageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CustomerShareImageEntity> page = this.page(
                new Query<CustomerShareImageEntity>().getPage(params),
                new QueryWrapper<CustomerShareImageEntity>()
        );

        return new PageUtils(page);
    }


}