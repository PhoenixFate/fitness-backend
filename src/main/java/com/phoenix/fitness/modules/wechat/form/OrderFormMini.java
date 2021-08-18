package com.phoenix.fitness.modules.wechat.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import com.phoenix.fitness.modules.pad.form.OrderForm;
import javax.validation.constraints.NotNull;

/**
 * 微信小程序订单表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
@ApiModel(value = "微信小程序订单表单")
public class OrderFormMini extends OrderForm {

    @ApiModelProperty(value = "openId")
    @NotNull(message="openId为空")
    private String openId;

}
