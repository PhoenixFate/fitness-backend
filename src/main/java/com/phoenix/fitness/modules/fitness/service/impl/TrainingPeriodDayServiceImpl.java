package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.TrainingPeriodDayDao;
import com.phoenix.fitness.modules.fitness.entity.TrainingPeriodDayEntity;
import com.phoenix.fitness.modules.fitness.service.TrainingPeriodDayService;
import java.util.Map;

@Service("trainingPeriodDayService")
public class TrainingPeriodDayServiceImpl extends ServiceImpl<TrainingPeriodDayDao, TrainingPeriodDayEntity> implements TrainingPeriodDayService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TrainingPeriodDayEntity> page = this.page(
                new Query<TrainingPeriodDayEntity>().getPage(params),
                new QueryWrapper<TrainingPeriodDayEntity>()
        );
        return new PageUtils(page);
    }

}