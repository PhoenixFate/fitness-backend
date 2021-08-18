package com.phoenix.fitness.modules.pad.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 教练注册表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
@ApiModel(value = "教练注册表单")
public class RegisterForm {

    @ApiModelProperty(value = "教练姓名")
    @NotBlank(message = "教练姓名不能为空")
    private String coachName;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "邮件")
    private String email;

    @ApiModelProperty(value = "身份证")
    private String identityCard;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "出生日期")
    private Date birthday;

    @ApiModelProperty(value = "头像")
    private String avatar;


    @ApiModelProperty(value = "单课价格")
    @NotBlank(message = "单课价格不能为空")
    private String perClassPrice;

    private List<GymEntity> gyms;
}
