package com.phoenix.fitness.modules.admin.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 销卡、销课表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class FinishTimesForm {
    /**
     * 合同id
     */
    private Long customerContractId;
    /**
     * 操作员姓名
     */
    private String operationName;
    /**
     * 从哪里销课、销卡的
     */
    private String client;
    /**
     * 销课时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date sureClassTime;
}
