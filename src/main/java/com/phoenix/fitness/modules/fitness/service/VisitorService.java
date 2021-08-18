package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.form.VisitorSearchForm;
import com.phoenix.fitness.modules.fitness.entity.VisitorEntity;

import java.text.ParseException;

/**
 * @author phoenix
 * @email sm516116978
 * @date 2021-07-20 16:03:42
 */
public interface VisitorService extends IService<VisitorEntity> {

    PageUtils queryPage(VisitorSearchForm visitorSearchForm) throws ParseException;

    void removeByDeleteFlag(Long id);

    boolean save(VisitorEntity visitorEntity);

    boolean updateById(VisitorEntity visitorEntity);
}

