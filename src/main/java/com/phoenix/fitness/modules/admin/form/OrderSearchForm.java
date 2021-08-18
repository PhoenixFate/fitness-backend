package com.phoenix.fitness.modules.admin.form;

import lombok.Data;

/**
 * 订单过滤表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class OrderSearchForm {
    /**
     * 订单编号
     */
    private String orderNumber;
    /**
     * 订单类型
     */
    private String orderType;
    /**
     * 顾客姓名
     */
    private String customerName;
    /**
     * 教练姓名
     */
    private String coachName;

}
