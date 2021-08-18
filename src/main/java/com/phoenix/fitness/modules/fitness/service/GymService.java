package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.GymWithCoachesDto;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import java.util.Map;

/**
 * 健身房Service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface GymService extends IService<GymEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean removeByDeleteFlag(Long id);

    Boolean save(GymWithCoachesDto gym);

    Boolean updateById(GymWithCoachesDto gym);

    GymWithCoachesDto getDetail(Long id);
}

