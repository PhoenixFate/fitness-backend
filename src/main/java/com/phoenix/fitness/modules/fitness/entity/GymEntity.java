package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 健身房
 * 1合作伙伴 : n健身房
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_gym")
public class GymEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long gymId;
	/**
	 * 健身房名称
	 */
	@NotBlank(message="健身房名称不能为空")
	@Size(max = 50,message = "健身房名称长度1-50")
	private String gymName;
	/**
	 * 健身房店面照片
	 */
	@Size(max = 512,message = "健身房店面照片长度0-512")
	private String gymImageUrl;
	/**
	 * 健身房所在城市
	 */
	@Size(max = 50,message = "健身房所在城市长度0-50")
	private String city;
	/**
	 * 健身房地址
	 */
	@Size(max = 512,message = "健身房地址长度0-512")
	private String address;
	/**
	 * 健身房所属合作伙伴
	 */
	@TableField(fill = FieldFill.UPDATE)
	@JsonIgnore
	private Long partnerId;
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
