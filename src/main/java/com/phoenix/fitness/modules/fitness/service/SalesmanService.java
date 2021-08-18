package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.entity.SalesmanEntity;
import java.util.Map;

/**
 *
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-06-24 10:45:05
 */
public interface SalesmanService extends IService<SalesmanEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void removeByDeleteFlag(Long id);
}

