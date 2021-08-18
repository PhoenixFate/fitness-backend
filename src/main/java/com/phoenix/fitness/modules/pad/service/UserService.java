package com.phoenix.fitness.modules.pad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.modules.pad.entity.UserEntity;
import com.phoenix.fitness.modules.pad.form.LoginForm;

/**
 * 用户
 *
 * @author Mark sm516116978@outlook.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回用户ID
	 */
	UserEntity login(LoginForm form);
}
