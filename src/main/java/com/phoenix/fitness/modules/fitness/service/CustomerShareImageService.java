package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.entity.CustomerShareImageEntity;
import java.util.Map;

/**
 * 
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-01-11 10:58:06
 */
public interface CustomerShareImageService extends IService<CustomerShareImageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

