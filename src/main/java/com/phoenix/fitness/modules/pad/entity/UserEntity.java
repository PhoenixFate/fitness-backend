package com.phoenix.fitness.modules.pad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
@TableName("tb_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	@TableId
	private Long userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 用户类型
	 * 1.客户
	 * 2.教练
	 */
	private Integer userType;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 删除标志
	 */
	private Integer deleteFlag;
	/**
	 * 一个教练一个账号，
	 * 一个教练可以在多个健身房中
	 * 表示当前教练账号是选那个健身房登录的
	 */
	@TableField(exist = false)
	private Long gymId;
	/**
	 * 一个教练一个账号，
	 * 一个教练可以在多个健身房中
	 * 表示当前教练账号是选那个健身房（关联的合作伙伴）登录的
	 */
	@TableField(exist = false)
	private Long partnerId;
}
