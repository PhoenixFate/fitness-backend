package com.phoenix.fitness.modules.admin.form;

import lombok.Data;

/**
 * 顾客过滤表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class CustomerSearchForm extends BaseSearchForm {
    /**
     * 顾客姓名
     */
    private String customerName;
    /**
     * pad端统一查询
     */
    private String searchValue;
    /**
     * 是否是会员
     */
    private Integer isVip;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 教练id
     */
    private String coachId;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 身份证
     */
    private String identityCard;
    /**
     * 实体卡号
     */
    private String physicalCardIndex;
    /**
     * 剩余会员天数最小值
     */
    private Integer vipLeftDaysMin;
    /**
     * 剩余会员天数最大值
     */
    private Integer vipLeftDaysMax;
    /**
     * 创建时间
     */
    private String createDate;
}
