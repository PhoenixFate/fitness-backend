package com.phoenix.fitness.modules.admin.form;

import lombok.Data;

/**
 * 密码表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class PasswordForm {
    /**
     * 原密码
     */
    private String password;
    /**
     * 新密码
     */
    private String newPassword;

}
