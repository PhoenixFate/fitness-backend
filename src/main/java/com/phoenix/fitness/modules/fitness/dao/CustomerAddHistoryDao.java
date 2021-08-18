package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.dto.CustomerCountDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerAddHistoryEntity;
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
public interface CustomerAddHistoryDao extends BaseMapper<CustomerAddHistoryEntity> {

    List<CustomerCountDto> selectCustomerCountPeriod(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
