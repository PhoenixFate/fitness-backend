package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.TargetCoachRelationEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 目标和教练 关联中间表
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-12-14 17:07:42
 */
@Mapper
public interface TargetCoachRelationDao extends BaseMapper<TargetCoachRelationEntity> {

}
