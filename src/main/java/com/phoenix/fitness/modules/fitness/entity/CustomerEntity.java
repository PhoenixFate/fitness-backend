package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanWithAllDto;

/**
 * 顾客实体
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_customer")
public class CustomerEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerId;
    /**
     * 姓名
     */
    private String name;
    /**
     * 姓名索引
     */
    private String nameIndex;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 邮箱
     */
    private String mail;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 身份证
     */
    private String identityCard;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 省份
     */
    private String province;
    /**
     * 城市
     */
    private String city;
    /**
     * 街道
     */
    private String county;
    /**
     * 教练id
     */
    private Long coachId;
    /**
     * 当前负责的教练姓名
     */
    @TableField(exist = false)
    private String coachName;
    /**
     * userId
     */
    private Long userId;
    /**
     * 客户编码
     */
    private Long customerNumber;
    /**
     * 训练目标
     */
    private Long goalId;
    /**
     * 训练目标名称
     */
    @TableField(exist = false)
    private String goalName;
    /**
     * 当前进度（非vip才有）
     */
    private Integer progressIndex;
    /**
     * 是否是vip
     * 1是
     * 0否
     */
    private Integer isVip;
    /**
     * vip开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date vipStartDate;
    /**
     * vip结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @TableField(fill = FieldFill.UPDATE)
    private Date vipEndDate;
    /**
     * 总计的课程
     */
    private Integer totalClass;
    /**
     * 当前进行的第几节课
     */
    private Integer currentClass;
    /**
     * 饮食计划id
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long dietPlanId;
    /**
     * 当前私教课教练id
     */
    private Long currentClassCoachId;
    /**
     * 微信openId
     */
    private String openId;
    /**
     * 添加顾客的日期
     */
    private Date createDate;
    /**
     * 开通vip日期
     */
    @JsonIgnore
    private Date openVipDate;
    /**
     * 实体卡号
     */
    private String physicalCardNumber;
    /**
     * 实体卡编号
     */
    private String physicalCardIndex;
    /**
     * 会员状态
     */
    private String status;
    /**
     * 顾客分类
     * 从未消费 NEVER_CONSUME
     * 第一次消费 FIRST_CONSUME
     * 多次消费 MORE_CONSUME
     */
    private String customerType;
    /**
     * 联系地址
     */
    private String address;

    @TableField(exist = false)
    private CustomerDietPlanEntity customerDietPlan;

    @TableField(exist = false)
    private BodyTestDataEntity bodyTestData;

    @TableField(exist = false)
    private BodyStatusDataEntity bodyStatusData;

    @TableField(exist = false)
    private CustomerPlanWithAllDto customerPlan;

    @TableField(exist = false)
    private List<CustomerContractEntity> contracts = new ArrayList<>();

    @TableField(exist = false)
    private Integer vipLeftDays;

    @TableField(exist = false)
    private List<CustomerReturnVisitEntity> customerReturnVisits;

}
