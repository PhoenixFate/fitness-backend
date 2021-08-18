package com.phoenix.fitness.modules.wechat.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.oss.service.SysOssService;

/**
 * 小程序端
 * 文件上传
 * @author Mark sm516116978@outlook.com
 */
@RestController
@RequestMapping("wechat/oss")
@RequiredArgsConstructor
public class WechatOssController {

	private final SysOssService sysOssService;

	/**
	 * 上传文件
	 */
	@PostMapping()
	@ApiOperation("上传文件")
	public ResponseEntity<R> upload(@RequestParam("file") MultipartFile file,@RequestParam(value = "prefix",required = false) String prefix) throws Exception {
		if (file.isEmpty()) {
			throw new FitnessException(ExceptionEnum.FILE_UPLOAD_EMPTY);
		}
		return ResponseEntity.ok(new R(sysOssService.saveOss(file,prefix)));
	}


	/**
	 * 多文件上传
	 */
	@PostMapping("/multi")
	@ApiOperation("上传文件")
	public ResponseEntity<R> uploadMore(@RequestParam("file") MultipartFile[]  fileList, @RequestParam(value = "prefix",required = false) String prefix) throws Exception {
		if (fileList.length==0) {
			throw new FitnessException(ExceptionEnum.FILE_UPLOAD_EMPTY);
		}
		return ResponseEntity.ok(new R(sysOssService.saveOssMulti(fileList,prefix)));
	}


}
