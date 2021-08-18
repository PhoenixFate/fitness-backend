package com.phoenix.fitness.modules.admin.dao;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.phoenix.fitness.modules.admin.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 * @author Mark sm516116978@outlook.com
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);
	
}
