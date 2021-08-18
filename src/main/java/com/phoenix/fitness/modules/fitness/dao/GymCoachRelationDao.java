package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.GymCoachRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 健身房-教练 中间关联表
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface GymCoachRelationDao extends BaseMapper<GymCoachRelationEntity> {

}
