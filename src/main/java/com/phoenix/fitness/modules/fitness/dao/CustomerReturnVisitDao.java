package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.CustomerReturnVisitEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * 顾客回访DAO
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface CustomerReturnVisitDao extends BaseMapper<CustomerReturnVisitEntity> {

    List<CustomerReturnVisitEntity> selectRemindList(Date date);

    List<Long> selectCustomerIds();
}
