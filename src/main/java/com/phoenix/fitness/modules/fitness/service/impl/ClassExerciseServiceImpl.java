package com.phoenix.fitness.modules.fitness.service.impl;

import com.phoenix.fitness.common.utils.Query;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.PageUtils;

import com.phoenix.fitness.modules.fitness.dao.ClassExerciseDao;
import com.phoenix.fitness.modules.fitness.entity.ClassExerciseRelationEntity;
import com.phoenix.fitness.modules.fitness.service.ClassExerciseService;


@Service("classExerciseService")
public class ClassExerciseServiceImpl extends ServiceImpl<ClassExerciseDao, ClassExerciseRelationEntity> implements ClassExerciseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ClassExerciseRelationEntity> page = this.page(
                new Query<ClassExerciseRelationEntity>().getPage(params),
                new QueryWrapper<ClassExerciseRelationEntity>()
        );

        return new PageUtils(page);
    }

}