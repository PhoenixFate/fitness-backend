package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_action_set_customer_image")
public class ActionSetCustomerImageEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long actionSetCustomerImageId;
    /**
     * 图片地址
     */
    private String imageUrl;
	/**
	 * 顾客id
	 */
    private Long customerId;
	/**
	 * 训练日id
	 */
	private Long customerPlanDayId;
	/**
	 * 动作组id
	 */
    private Long actionSetCustomerId;
}
