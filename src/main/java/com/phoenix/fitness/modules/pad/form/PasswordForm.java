package com.phoenix.fitness.modules.pad.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 修改密码表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
@ApiModel(value = "修改密码表单")
public class PasswordForm {


    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    @Size(min = 6,max = 20,message = "密码长度6-20")
    private String oldPassword;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6,max = 20,message = "密码长度6-20")
    private String newPassword;

}
