package com.phoenix.fitness.modules.admin.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 合同过滤表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class ContractSearchForm extends BaseSearchForm {
    /**
     * 实体卡号
     */
    private String physicalCardIndex;
    /**
     * 顾客姓名
     */
    private String customerName;
    /**
     * 合同名称
     */
    private String contractName;
    /**
     * 合同类型
     */
    private String contractType;
    /**
     * 合同状态
     */
    private String contractStatus;
    /**
     * 私教类型
     */
    private String trainingType;
    /**
     * 会员卡类别
     */
    private String vipCardBigType;
    /**
     * 会员卡分类
     */
    private String vipCardType;
    /**
     * 支付金额最大
     */
    private Double payMoneyMax;
    /**
     * 支付金额最小
     */
    private Double payMoneyMin;
    /**
     * 激活日期最大
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date activeDateMax;
    /**
     * 激活日期最小
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date activeDateMin;
    /**
     * 销售姓名
     */
    private String salesmanName;
    /**
     * 教练姓名
     */
    private String coachName;
    /**
     * 渠道
     */
    private String channel;
    /**
     * 是否老合同
     */
    private Integer isOld;
    /**
     * 支付方式
     */
    private String payType;

}
