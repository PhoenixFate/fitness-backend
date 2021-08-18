package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.modules.fitness.dao.OrderItemDao;
import com.phoenix.fitness.modules.fitness.entity.OrderItemEntity;
import com.phoenix.fitness.modules.fitness.service.OrderItemService;

@Service("orderItemService")
@AllArgsConstructor
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {


}