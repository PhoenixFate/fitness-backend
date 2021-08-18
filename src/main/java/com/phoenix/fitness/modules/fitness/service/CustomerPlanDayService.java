package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithAllDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithClassInfoDto;
import com.phoenix.fitness.modules.fitness.dto.MonthClassCountDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanDayEntity;
import com.phoenix.fitness.modules.fitness.vo.CustomerCommentVO;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.Date;
import java.util.List;

/**
 * 训练指导service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface CustomerPlanDayService extends IService<CustomerPlanDayEntity> {

    List<CustomerPlanDayEntity> listOneDay(Date date, UserEntity user);

    CustomerPlanDayWithAllDto getDetail(Long id);

    void complete(CustomerPlanDayWithAllDto customerPlanDay);

    List<CustomerPlanDayEntity> listOneCustomerPlan(Long customerPlanId);

    List<CustomerPlanDayEntity> listOneCustomerHistory(Long customerPlanId);

    List<MonthClassCountDto> listOneMonthCount(Long year, Long month,UserEntity user);

    List<CustomerPlanDayWithClassInfoDto> customerAllDays(Long customerId);

    void customerSure(CustomerCommentVO date);

    Boolean updateById(CustomerPlanDayWithAllDto customerPlanDay);
}

