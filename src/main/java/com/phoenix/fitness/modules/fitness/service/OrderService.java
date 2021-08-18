package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.OrderWithItemsDto;
import com.phoenix.fitness.modules.fitness.entity.OrderEntity;
import com.phoenix.fitness.modules.pad.form.OrderForm;

import java.util.Map;

/**
 * 订单service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    OrderWithItemsDto getDetailByOrderNumber(String orderNumber);

    OrderWithItemsDto getDetail(Long id);

    OrderEntity preOrder(OrderForm orderForm);

    void afterNotify(OrderEntity orderEntity);
}

