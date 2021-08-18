package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.fitness.entity.PayPreOrderLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付预下单请求日志
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface PayPreOrderLogDao extends BaseMapper<PayPreOrderLogEntity> {

}
