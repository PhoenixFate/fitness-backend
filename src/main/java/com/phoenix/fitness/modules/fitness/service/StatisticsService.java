package com.phoenix.fitness.modules.fitness.service;

import com.phoenix.fitness.modules.fitness.dto.AdminHomeToday;
import com.phoenix.fitness.modules.fitness.dto.CustomerCountDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerMoneyDto;
import com.phoenix.fitness.modules.fitness.vo.PeriodVO;

import java.util.List;

/**
 * 统计数据service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
public interface StatisticsService{

    AdminHomeToday getAdminToday();

    List<CustomerCountDto> getCustomerCountPeriod(PeriodVO periodVO);

    List<CustomerCountDto> getVipCustomerCountPeriod(PeriodVO periodVO);

    List<CustomerMoneyDto> getCustomerMoneyPeriod(PeriodVO periodVO);
}

