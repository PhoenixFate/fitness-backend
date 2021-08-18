package com.phoenix.fitness.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.oss.entity.SysOssEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * 文件上传
 *
 * @author Mark sm516116978@outlook.com
 */
public interface SysOssService extends IService<SysOssEntity> {

	PageUtils queryPage(Map<String, Object> params);

    String saveOss(MultipartFile file, String prefix) throws IOException;

    ArrayList<String> saveOssMulti(MultipartFile[] fileList, String prefix) throws IOException;

    String saveOssBase64(String base64Str, String prefix);
}
