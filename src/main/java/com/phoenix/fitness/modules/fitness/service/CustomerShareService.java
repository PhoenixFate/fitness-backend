package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.CustomerShareWithImagesDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerShareEntity;

import java.util.Map;

/**
 * 顾客分享service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-01-11 10:58:06
 */
public interface CustomerShareService extends IService<CustomerShareEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean removeByDeleteFlag(Long id);

    Boolean save(CustomerShareWithImagesDto customerShare);

    Boolean updateById(CustomerShareWithImagesDto customerShare);

    CustomerShareWithImagesDto getDetail(Long id);

    PageUtils oneCustomerList(Map<String, Object> params,Long customerId);
}

