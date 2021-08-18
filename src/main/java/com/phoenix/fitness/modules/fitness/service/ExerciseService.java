package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.ExerciseWithActionSetDto;
import com.phoenix.fitness.modules.fitness.entity.ExerciseEntity;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;
import java.util.Map;

/**
 * 训练项service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
public interface ExerciseService extends IService<ExerciseEntity> {

    PageUtils queryPage(Map<String, Object> params, UserEntity user);

    Boolean removeByDeleteFlag(Long id);

    ExerciseWithActionSetDto getDetail(Long id);

    Boolean save(ExerciseWithActionSetDto exercise);

    Boolean updateById(ExerciseWithActionSetDto exercise);

    List<ExerciseWithActionSetDto> listWithDetail(UserEntity user);
}

