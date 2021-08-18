package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.dto.ExerciseWithActionSetDto;
import com.phoenix.fitness.modules.fitness.entity.ExerciseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;

/**
 * 训练项DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface ExerciseDao extends BaseMapper<ExerciseEntity> {

    ExerciseWithActionSetDto selectExerciseWithActionSet(Long id);

    Integer deleteByDeleteFlag(Long ids);

    List<ExerciseWithActionSetDto> selectListWithDetail(@Param("user") UserEntity user);

}
