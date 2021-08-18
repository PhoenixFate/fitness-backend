package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.TrainingPlanWithPeriodsDto;
import com.phoenix.fitness.modules.fitness.entity.TrainingPlanEntity;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;

/**
 * 训练计划DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Mapper
public interface TrainingPlanDao extends BaseMapper<TrainingPlanEntity> {

    TrainingPlanWithPeriodsDto selectPlanWithDetail(Long id);

    List<TrainingPlanWithPeriodsDto> selectPlanListWithDetail(@Param("user") UserEntity user);

    Integer deleteByDeleteFlag(Long id);

    IPage<TrainingPlanEntity> selectPlanWithGoal(IPage<TrainingPlanEntity> pageParams, @Param("searchValue") String searchValue, @Param("user") UserEntity user);


}
