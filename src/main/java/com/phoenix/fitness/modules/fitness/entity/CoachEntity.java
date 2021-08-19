package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * 教练实体
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_coach")
public class CoachEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long coachId;
	/**
	 * 教练姓名
	 */
	private String coachName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 账号名称
	 */
	private String username;
	/**
	 * 性别
	 */
	private Integer gender;
	/**
	 * 训练方向
	 */
	private String exerciseDirection;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 身份证
	 */
	private String identityCard;
	/**
	 * 生日
	 */
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	private Date birthday;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 每节课程费用
	 */
	private String perClassPrice;
	/**
	 * 教练关联的账号id
	 */
	private Long userId;
	/**
	 * 创建时间
	 * 默认当前时间
	 */
	@JsonIgnore
	private Date createTime;
	/**
	 * 修改时间
	 * 默认当前时间
	 */
	@JsonIgnore
	private Date updateTime;
	/**
	 * 是否删除
	 * 0未删除
	 * 1已删除
	 * 默认0
	 */
	@JsonIgnore
	private Integer deleteFlag;

}
