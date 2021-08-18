package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.GymCoachRelationDao;
import com.phoenix.fitness.modules.fitness.entity.GymCoachRelationEntity;
import com.phoenix.fitness.modules.fitness.service.GymCoachRelationService;

@Service("gymCoachRelationService")
public class GymCoachRelationServiceImpl extends ServiceImpl<GymCoachRelationDao, GymCoachRelationEntity> implements GymCoachRelationService {


}