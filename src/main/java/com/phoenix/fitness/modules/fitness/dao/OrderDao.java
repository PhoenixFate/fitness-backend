package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.OrderWithItemsDto;
import com.phoenix.fitness.modules.fitness.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.phoenix.fitness.modules.admin.form.OrderSearchForm;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

/**
 * 订单DAO
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    OrderWithItemsDto selectOrderWithDetail(Long id);

    OrderWithItemsDto selectOrderDetailWithNumber(String orderNumber);

    IPage<OrderEntity> selectOrderListWithDetail(IPage<OrderEntity> pageParams, @Param("searchForm") OrderSearchForm searchForm, @Param("user") UserEntity user);

}
