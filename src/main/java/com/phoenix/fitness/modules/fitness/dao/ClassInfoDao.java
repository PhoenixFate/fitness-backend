package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.dto.ClassInfoWithExercisesDto;
import com.phoenix.fitness.modules.fitness.entity.ClassInfoEntity;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;

/**
 * 课程DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface ClassInfoDao extends BaseMapper<ClassInfoEntity> {

    ClassInfoWithExercisesDto selectClassInfoWithDetail(Long id);

    List<ClassInfoWithExercisesDto> selectClassInfoListWithDetail(@Param("user") UserEntity user);

    Integer deleteByDeleteFlag(Long id);
}
