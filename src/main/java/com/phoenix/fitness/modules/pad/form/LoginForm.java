package com.phoenix.fitness.modules.pad.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 登录表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
@ApiModel(value = "登录表单")
public class LoginForm {
    @ApiModelProperty(value = "用户名")
    @NotBlank(message="用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

    @ApiModelProperty(value = "用户类型")
    @NotNull(message="用户类型不能为空")
    private Integer userType;

    @ApiModelProperty(value = "健身房id")
    @NotNull(message="健身房id不能为空")
    private Long gymId;
}
