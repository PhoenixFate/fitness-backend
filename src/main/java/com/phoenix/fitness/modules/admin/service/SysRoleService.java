

package com.phoenix.fitness.modules.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @author Mark sm516116978@outlook.com
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);

	void deleteBatch(Long[] roleIds);

	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
