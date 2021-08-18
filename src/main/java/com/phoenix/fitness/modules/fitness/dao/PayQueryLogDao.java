package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.PayQueryLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付订单查询日志
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface PayQueryLogDao extends BaseMapper<PayQueryLogEntity> {

}
