package com.phoenix.fitness.modules.admin.form;

import lombok.Data;
/**
 * 登录表单
 * @author Mark sm516116978@outlook.com
 */
@Data
public class SysLoginForm {
    private String username;
    private String password;
    private String captcha;
    private String uuid;
}
