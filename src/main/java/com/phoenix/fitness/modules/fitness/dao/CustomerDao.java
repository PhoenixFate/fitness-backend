package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.CustomerCountDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;

/**
 * 顾客DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface CustomerDao extends BaseMapper<CustomerEntity> {

    CustomerEntity selectCustomerWithDetail(Long id);

    List<CustomerCountDto> selectVipCustomerCountPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate);

    IPage<CustomerEntity> selectCustomerPage(IPage<CustomerEntity> pageParams,
                                             @Param("searchForm") CustomerSearchForm searchForm, @Param("user") UserEntity user);

    IPage<CustomerEntity> selectCustomerMarketCardPage(IPage<CustomerEntity> pageParams,
                                                       @Param("searchForm") CustomerSearchForm searchForm);

}
