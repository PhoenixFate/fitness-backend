package com.phoenix.fitness.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.admin.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 角色管理
 *
 * @author Mark sm516116978@outlook.com
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	
	/**
	 * 查询用户创建的角色ID列表
	 */
	List<Long> queryRoleIdList(Long createUserId);
}
