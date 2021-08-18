

package com.phoenix.fitness.modules.admin.service;

import com.phoenix.fitness.modules.admin.entity.SysUserEntity;
import com.phoenix.fitness.modules.admin.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author Mark sm516116978@outlook.com
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity queryUser(Long userId);
}
