package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.TrainingPeriodDao;
import com.phoenix.fitness.modules.fitness.entity.TrainingPeriodEntity;
import com.phoenix.fitness.modules.fitness.service.TrainingPeriodService;
import java.util.Map;


@Service("trainingPeriodService")
public class TrainingPeriodServiceImpl extends ServiceImpl<TrainingPeriodDao, TrainingPeriodEntity> implements TrainingPeriodService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TrainingPeriodEntity> page = this.page(
                new Query<TrainingPeriodEntity>().getPage(params),
                new QueryWrapper<TrainingPeriodEntity>()
        );

        return new PageUtils(page);
    }

}