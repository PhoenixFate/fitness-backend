package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.dto.PeriodDto;
import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;

import java.util.List;

/**
 * 顾客营销service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface CustomerMarketingService extends IService<CustomerEntity> {

  PageUtils queryCardPage(CustomerSearchForm customerSearchForm);

  PageUtils queryTrainingPage(CustomerSearchForm customerSearchForm);

    List<PeriodDto> periodCount();

    List<List<Integer>> periodCountChart();
}

