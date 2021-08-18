package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 合作伙伴
 * 1合作伙伴 : n健身房
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_partner")
public class PartnerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long partnerId;
	/**
	 * 合作伙伴名称
	 */
	@NotBlank(message="合作伙伴名称不能为空")
	@Size(max = 80,message = "合作伙伴名称长度1-80")
	private String partnerName;
	/**
	 * 老板姓名
	 */
	private String bossName;
	/**
	 * 老板联系电话
	 */
	private String bossMobile;
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
	/**
	 * 名下健身房数量
	 */

	@TableField(exist = false)
	private Integer gymCount;
}
