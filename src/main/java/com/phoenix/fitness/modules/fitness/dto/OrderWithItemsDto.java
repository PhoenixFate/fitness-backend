package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.OrderEntity;
import com.phoenix.fitness.modules.fitness.entity.OrderItemEntity;
import lombok.Data;

import java.util.List;

/**
 * 带订单子项的订单详情
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class OrderWithItemsDto extends OrderEntity {
	/**
	 * 关联的顾客
	 */
	private CustomerEntity customer;
	/**
	 * 关联的教练
	 */
	private CoachEntity coach;
	/**
	 * 订单子项
	 */
	private List<OrderItemEntity> orderItems;
}
