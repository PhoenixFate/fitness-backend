package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.ClassInfoWithExercisesDto;
import com.phoenix.fitness.modules.fitness.entity.ClassInfoEntity;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 课程DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface ClassInfoService extends IService<ClassInfoEntity> {

    PageUtils queryPage(Map<String, Object> params, UserEntity user);

    List<ClassInfoWithExercisesDto> selectListWithDetail(UserEntity user);

    Boolean removeByDeleteFlag(Long ids);

    ClassInfoWithExercisesDto getDetail(Long id);

    Boolean save(ClassInfoWithExercisesDto classInfo);

    Boolean updateById(ClassInfoWithExercisesDto classInfo);
}

