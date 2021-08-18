package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.entity.EquipmentEntity;
import java.util.List;
import java.util.Map;

/**
 * 设备service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface EquipmentService extends IService<EquipmentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean removeByDeleteFlag(Long id);

    Boolean removeBatchByDeleteFlag(List<Long> ids);
}

