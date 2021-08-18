package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.modules.fitness.entity.CustomerReturnVisitEntity;

import java.util.Date;
import java.util.List;

/**
 * 顾客回访
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
public interface CustomerReturnVisitService extends IService<CustomerReturnVisitEntity> {

    List<CustomerReturnVisitEntity> getOneCustomerAllReturnVisit(Long customerId);

    void saveCustomerReturnVisit(CustomerReturnVisitEntity customerReturnVisitEntity);

    List<CustomerReturnVisitEntity> remind(Date date);
}

