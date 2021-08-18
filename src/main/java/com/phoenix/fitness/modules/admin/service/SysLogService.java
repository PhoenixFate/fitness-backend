

package com.phoenix.fitness.modules.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sm516116978@outlook.com
 */
public interface SysLogService extends IService<SysLogEntity> {

    PageUtils queryPage(Map<String, Object> params);

}
