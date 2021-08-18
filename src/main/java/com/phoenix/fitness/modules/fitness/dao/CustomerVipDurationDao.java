package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.CustomerVipDurationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface CustomerVipDurationDao extends BaseMapper<CustomerVipDurationEntity> {

    List<CustomerVipDurationEntity> selectCustomerVipDurationList(Long customerId);
}
