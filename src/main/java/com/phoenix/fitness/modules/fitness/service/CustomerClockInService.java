package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.entity.CustomerClockInEntity;

import java.util.Map;

/**
 * 顾客打卡
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-05-11 16:23:55
 */
public interface CustomerClockInService extends IService<CustomerClockInEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean clockIn(Long customerId);

    Boolean clockOut(Long customerId);
}

