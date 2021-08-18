package com.phoenix.fitness.modules.pad.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 订单表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
@ApiModel(value = "订单表单")
public class OrderForm {

    @ApiModelProperty(value = "用户id")
    @NotNull(message="用户名id为空")
    private Long customerId;

    @ApiModelProperty(value = "教练id")
    @NotNull(message="教练id为空")
    private Long coachId;

    @ApiModelProperty(value = "订单类型")
    @NotBlank(message="订单类型为空")
    private String orderType;

    @ApiModelProperty(value = "订单子项")
    @NotNull(message="订单子项目为空")
    @Valid
    private List<OrderItemForm> orderItems;
}
