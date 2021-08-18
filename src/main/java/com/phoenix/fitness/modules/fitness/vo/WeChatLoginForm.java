package com.phoenix.fitness.modules.fitness.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * 微信小程序登录表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
@ApiModel(value = "微信小程序登录表单")
public class WeChatLoginForm {

    @ApiModelProperty(value = "昵称")
    @NotBlank(message="昵称不能为空")
    private String nickName;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "手机")
    @NotBlank(message="手机不能为空")
    private String mobile;

    @ApiModelProperty(value = "openId")
    @NotBlank(message="openId不能为空")
    private String openId;
}
