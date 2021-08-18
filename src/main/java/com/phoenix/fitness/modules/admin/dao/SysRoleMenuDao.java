package com.phoenix.fitness.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.admin.entity.SysRoleMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 角色与菜单对应关系
 *
 * @author Mark sm516116978@outlook.com
 */
@Mapper
public interface SysRoleMenuDao extends BaseMapper<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);

	/**
	 * 根据角色ID数组，批量删除
	 */
	int deleteBatch(Long[] roleIds);
}
