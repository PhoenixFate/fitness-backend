package com.phoenix.fitness.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.admin.entity.SysMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 菜单管理
 *
 * @author Mark sm516116978@outlook.com
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();

}
