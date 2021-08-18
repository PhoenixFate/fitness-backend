package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 顾客分享的照片
 * 顾客单次分享1 : n张分享图片
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-01-11 10:58:06
 */
@Data
@TableName("tb_customer_share_image")
public class CustomerShareImageEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 顾客id
	 */
	private Long customerId;
	/**
	 * 顾客分享id
	 */
	private Long customerShareId;
	/**
	 * 顾客分享的照片
	 */
	private String customerShareImage;
}
