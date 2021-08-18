package com.phoenix.fitness.modules.oss.controller;

import com.google.gson.Gson;
import com.phoenix.fitness.modules.admin.service.SysConfigService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.ConfigConstant;
import com.phoenix.fitness.common.utils.Constant;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.common.validator.ValidatorUtils;
import com.phoenix.fitness.common.validator.group.AliyunGroup;
import com.phoenix.fitness.common.validator.group.QcloudGroup;
import com.phoenix.fitness.common.validator.group.QiniuGroup;
import com.phoenix.fitness.modules.oss.cloud.CloudStorageConfig;
import com.phoenix.fitness.modules.oss.service.SysOssService;

import java.util.Map;

/**
 * 文件上传
 *
 * @author Mark sm516116978@outlook.com
 */
@RestController
@RequestMapping("sys/oss")
@RequiredArgsConstructor
public class SysOssController {

    private final SysOssService sysOssService;

    private final SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

    /**
     * 列表
     */
    @GetMapping()
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysOssService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 云存储配置信息
     */
    @GetMapping("/config")
    public R config() {
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
        return R.ok().put("config", config);
    }

    /**
     * 保存云存储配置信息
     */
    @PostMapping("config")
    public R saveConfig(@RequestBody CloudStorageConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config);
        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            //校验七牛数据
            ValidatorUtils.validateEntity(config, QiniuGroup.class);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            //校验阿里云数据
            ValidatorUtils.validateEntity(config, AliyunGroup.class);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
            //校验腾讯云数据
            ValidatorUtils.validateEntity(config, QcloudGroup.class);
        }
        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));
        return R.ok();
    }


    /**
     * 上传文件
     */
    @PostMapping()
    @ApiOperation("上传文件")
    public ResponseEntity<R> upload(@RequestParam("file") MultipartFile file, @RequestParam(value = "prefix", required = false) String prefix) throws Exception {
        if (file.isEmpty()) {
            throw new FitnessException(ExceptionEnum.FILE_UPLOAD_EMPTY);
        }
        return ResponseEntity.ok(new R(sysOssService.saveOss(file, prefix)));
    }

    /**
     * 上传文件
     * base64图片
     */
    @PostMapping("/base64")
    @ApiOperation("上传文件-base64图片")
    public ResponseEntity<R> uploadBase64Image(@RequestParam("base64Str") String base64Str, @RequestParam(value = "prefix", required = false) String prefix) throws Exception {
        String s = sysOssService.saveOssBase64(base64Str, prefix);
        return ResponseEntity.ok(new R(s));
    }


    /**
     * 多文件上传
     */
    @PostMapping("/multi")
    @ApiOperation("上传文件")
    public ResponseEntity<R> uploadMore(@RequestParam("file") MultipartFile[] fileList, @RequestParam(value = "prefix", required = false) String prefix) throws Exception {
        if (fileList.length == 0) {
            throw new FitnessException(ExceptionEnum.FILE_UPLOAD_EMPTY);
        }
        return ResponseEntity.ok(new R(sysOssService.saveOssMulti(fileList, prefix)));
    }


}
