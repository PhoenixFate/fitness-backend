package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.entity.CustomerClockInEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 顾客打卡DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-05-11 16:23:55
 */
@Mapper
public interface CustomerClockInDao extends BaseMapper<CustomerClockInEntity> {

    IPage<CustomerClockInEntity> selectCustomerClockInList(IPage<CustomerClockInEntity> pageParams, @Param("customerName") String customerName, @Param("customerId") Long customerId);

}
