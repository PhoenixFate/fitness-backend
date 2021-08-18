

package com.phoenix.fitness.modules.oss.cloud;


import com.phoenix.fitness.modules.admin.service.SysConfigService;
import com.phoenix.fitness.common.utils.ConfigConstant;
import com.phoenix.fitness.common.utils.Constant;
import com.phoenix.fitness.common.utils.SpringContextUtils;

/**
 * 文件上传Factory
 *
 * @author Mark sm516116978@outlook.com
 */
public final class AliOSSFactory {
  private static SysConfigService sysConfigService;

  public static CloudStorageService build() {
    if (sysConfigService == null) {
      sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }
    //获取云存储配置信息
    CloudStorageConfig config = sysConfigService
        .getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

    if (config.getType() == Constant.CloudService.QINIU.getValue()) {
      return new QiniuCloudStorageService(config);
    } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
      return new AliyunCloudStorageService(config);
    } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
      return new QcloudCloudStorageService(config);
    }

    return null;
  }

}
