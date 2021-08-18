package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.CustomerShareWithImagesDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerShareEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 顾客分享DAO
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-01-11 10:58:06
 */
@Mapper
public interface CustomerShareDao extends BaseMapper<CustomerShareEntity> {

    Integer deleteByDeleteFlag(Long id);

    IPage<CustomerShareEntity> selectCustomerShares(IPage<CustomerShareEntity> pageParams, Long customerId,String customerName);

    CustomerShareWithImagesDto selectOneDetail(Long id);

}
