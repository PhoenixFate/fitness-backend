package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.dto.CustomerMoneyDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerChargeHistoryEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 
 * 
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface CustomerChargeHistoryDao extends BaseMapper<CustomerChargeHistoryEntity> {

    List<CustomerMoneyDto> selectCustomerMoneyPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
