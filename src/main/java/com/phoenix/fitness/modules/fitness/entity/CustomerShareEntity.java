package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 顾客分享（在小程序端分享）
 * 1顾客分享 : n分享图片
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-01-11 10:58:06
 */
@Data
@TableName("tb_customer_share")
public class CustomerShareEntity extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long customerShareId;
	/**
	 * 顾客id
	 */
	@NotNull(message = "顾客id不能为空")
	private Long customerId;
	/**
	 * 顾客姓名
	 */
	@TableField(exist = false)
	private String customerName;
	/**
	 * 顾客头像
	 */
	@TableField(exist = false)
	private String customerAvatar;
	/**
	 * 分享的文本
	 */
	@NotEmpty(message = "分享的文本不能为空")
	@Size(max = 512,message = "分享的文本最大长度为512个字符")
	private String shareText;
	/**
	 * 分享的时间点
	 */
	private Date shareTime;
}
