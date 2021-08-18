package com.phoenix.fitness.modules.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.admin.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 * @author Mark sm516116978@outlook.com
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
