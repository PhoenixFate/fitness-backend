package com.phoenix.fitness.modules.fitness.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 微信小程序解密手机表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
@ApiModel(value = "微信小程序解密手机表单")
public class DecryMobileForm {

    @ApiModelProperty(value = "加密数据")
    @NotBlank(message="加密数据不能为空")
    private String encryptedData;

    @ApiModelProperty(value = "iv")
    @NotBlank(message="iv不能为空")
    private String iv;

    @ApiModelProperty(value = "sessionKey")
    @NotBlank(message="sessionKey不能为空")
    private String sessionKey;
}
