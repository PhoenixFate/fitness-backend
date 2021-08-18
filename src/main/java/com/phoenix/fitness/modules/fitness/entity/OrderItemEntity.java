package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;

/**
 * 订单子项
 * 1订单 : n订单子项
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_order_item")
public class OrderItemEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long orderItemId;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 订单子项类型
	 */
	private String orderItemType;
	/**
	 * 订单子项单价
	 */
	private String itemPrice;
	/**
	 * 订单子项数量
	 */
	private Integer itemCount;
	/**
	 * 订单子项总价
	 */
	private String itemTotalPrice;
	/**
	 * 训练计划id
	 */
	private Long trainingPlanId;
	/**
	 * 会员卡id
	 */
	private Long vipCardId;

}
