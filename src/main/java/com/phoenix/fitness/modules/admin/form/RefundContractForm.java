package com.phoenix.fitness.modules.admin.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 合同退款表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class RefundContractForm {
    /**
     * 合同id
     */
    private Long customerContractId;
    /**
     * 退款金额
     */
    @NotEmpty(message = "退款金额不能为空")
    private String refundMoney;
    /**
     * 退款支付方式
     */
    @NotEmpty(message = "退款支付方式不能为空")
    private String refundPayType;
    /**
     * 退款日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull(message = "退款日期不能为空")
    private Date refundDate;
}
