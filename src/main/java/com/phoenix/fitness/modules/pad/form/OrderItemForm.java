package com.phoenix.fitness.modules.pad.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 订单子项表单
 * @author Mark sm516116978@outlook.com
 */
@Data
@ApiModel(value = "订单子项表单")
public class OrderItemForm {

    @ApiModelProperty(value = "数量")
    @NotNull(message="数量为空")
    private Integer itemCount;

    @ApiModelProperty(value = "订单子项类型")
    @NotBlank(message="订单子项类型为空")
    private String orderItemType;

    private Long trainingPlanId;

    @ApiModelProperty(value = "VIP卡")
    private Long vipCardId;
}
