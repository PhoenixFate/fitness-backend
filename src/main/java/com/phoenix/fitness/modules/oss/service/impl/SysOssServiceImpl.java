package com.phoenix.fitness.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.FileUtil;
import com.phoenix.fitness.common.utils.ImageBase64Converter;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.oss.cloud.AliOSSFactory;
import com.phoenix.fitness.modules.oss.dao.SysOssDao;
import com.phoenix.fitness.modules.oss.entity.SysOssEntity;
import com.phoenix.fitness.modules.oss.service.SysOssService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Service("sysOssService")
@AllArgsConstructor
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

    private final SysOssDao sysOssDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysOssEntity> page = this.page(
                new Query<SysOssEntity>().getPage(params)
        );
        return new PageUtils(page);
    }

    @Override
    public String saveOss(MultipartFile file, String prefix) throws IOException {
        //上传文件
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
      String url = AliOSSFactory.build().uploadSuffix(file.getBytes(), suffix, prefix);
        //保存记录
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        sysOssDao.insert(ossEntity);
        return url;
    }

    @Override
    public ArrayList<String> saveOssMulti(MultipartFile[] fileList, String prefix) throws IOException {
        ArrayList<String> urlList = new ArrayList<>();
        for (MultipartFile file : fileList) {
            String url = this.saveOss(file, prefix);
            urlList.add(url);
        }
        return urlList;
    }

    @Override
    public String saveOssBase64(String base64Str, String prefix) {
        File file = null;
        if (base64Str.contains("data:image/jpeg;base64,")) {
            base64Str = base64Str.replaceAll("data:image/jpeg;base64,", "");
            file = ImageBase64Converter.convertBase64ToFile(base64Str, "uploadImage", UUID.randomUUID().toString() + ".jpg");
        } else if (base64Str.contains("data:image/png;base64,")) {
            base64Str = base64Str.replaceAll("data:image/png;base64,", "");
            file = ImageBase64Converter.convertBase64ToFile(base64Str, "uploadImage", UUID.randomUUID().toString() + ".png");
        } else {
            throw new FitnessException(ExceptionEnum.BASE64_FILE_TYPE_ERROR);
        }
        //上传文件
        byte[] bytesByFile = FileUtil.getBytesByFile(file.getPath());
        String suffix = file.getName().substring(file.getName().lastIndexOf("."));
      String url = AliOSSFactory.build().uploadSuffix(bytesByFile, suffix, prefix);
        //保存记录
        SysOssEntity ossEntity = new SysOssEntity();
        ossEntity.setUrl(url);
        sysOssDao.insert(ossEntity);
        return url;
    }

}
