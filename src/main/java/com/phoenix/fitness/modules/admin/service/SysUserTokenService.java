

package com.phoenix.fitness.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.entity.SysUserTokenEntity;

/**
 * 用户Token
 *
 * @author Mark sm516116978@outlook.com
 */
public interface SysUserTokenService extends IService<SysUserTokenEntity> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
