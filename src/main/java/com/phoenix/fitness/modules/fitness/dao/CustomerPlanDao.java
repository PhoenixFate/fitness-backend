package com.phoenix.fitness.modules.fitness.dao;

import com.phoenix.fitness.modules.fitness.dto.CustomerPlanWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface CustomerPlanDao extends BaseMapper<CustomerPlanEntity> {

    List<CustomerPlanWithAllDto> selectCustomerPlanList(Long customerId);

}
