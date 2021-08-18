package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.TargetWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.TargetEntity;
import com.phoenix.fitness.modules.fitness.vo.TargetDeleteCoachVO;
import com.phoenix.fitness.modules.fitness.vo.TargetUpdateCoachVO;

import java.util.Map;

/**
 * 目标service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-12-14 17:07:42
 */
public interface TargetService extends IService<TargetEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void removeByDeleteFlag(Long id);

    TargetWithAllDto getDetail(Long id);

    boolean save(TargetWithAllDto target);

    boolean updateById(TargetWithAllDto target);

    void updateCoachRelation(TargetUpdateCoachVO target);

    void deleteCoachRelation(TargetDeleteCoachVO target);
}

