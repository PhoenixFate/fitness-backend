package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.TargetCoachRelationDao;
import com.phoenix.fitness.modules.fitness.entity.TargetCoachRelationEntity;
import com.phoenix.fitness.modules.fitness.service.TargetCoachRelationService;

@Service("targetCoachService")
@AllArgsConstructor
public class TargetCoachRelationServiceImpl extends ServiceImpl<TargetCoachRelationDao, TargetCoachRelationEntity> implements TargetCoachRelationService {



}