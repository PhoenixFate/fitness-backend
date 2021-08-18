package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.entity.TrainingGoalEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 训练目标DAO
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Mapper
public interface TrainingGoalDao extends BaseMapper<TrainingGoalEntity> {

    Integer deleteByDeleteFlag(Long id);
}
