package com.phoenix.fitness.modules.admin.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 账单搜索表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class BillSearchForm extends BaseSearchForm {

    /**
     * 开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "开始日期不能为空")
    private Date startDate;
    /**
     * 结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "结束日期不能为空")
    private Date endDate;

    /**
     * 合同类型
     */
    private String contractType;
    /**
     * 是否在搜索时间段内
     */
    private String contractPeriodFilter;
    /**
     * 支付方式
     */
    private String payType;
    /**
     * 支付金额最大
     */
    private Double payMoneyMax;
    /**
     * 支付金额最小
     */
    private Double payMoneyMin;
    /**
     * 会员卡类别
     */
    private String vipCardBigType;
    /**
     * 会员卡分类
     */
    private String vipCardType;
    /**
     * 私教类型
     */
    private String trainingType;
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
     * 顾客姓名
     */
    private String customerName;
}
