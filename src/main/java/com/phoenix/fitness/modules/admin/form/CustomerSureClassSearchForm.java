package com.phoenix.fitness.modules.admin.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 销课记录搜索表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class CustomerSureClassSearchForm extends BaseSearchForm {
    /**
     * 顾客姓名
     */
    private String customerName;
    /**
     * 教练姓名
     */
    private String coachName;
    /**
     * 操作员姓名
     */
    private String operationName;
    /**
     * 销课时间-开始日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sureClassTimeStart;
    /**
     * 销课日期-结束日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date sureClassTimeEnd;
    /**
     * 合同类型
     */
    private String contractType;
}
