package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.CustomerDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerShareDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerShareImageDao;
import com.phoenix.fitness.modules.fitness.dto.CustomerShareWithImagesDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerShareEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerShareImageEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerShareImageService;
import com.phoenix.fitness.modules.fitness.service.CustomerShareService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("customerShareService")
@AllArgsConstructor
public class CustomerShareServiceImpl extends ServiceImpl<CustomerShareDao, CustomerShareEntity> implements CustomerShareService {

    private final CustomerShareDao customerShareDao;

    private final CustomerShareImageService customerShareImageService;

    private final CustomerShareImageDao customerShareImageDao;

    private final CustomerDao customerDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Long customerId = null;
        if (!StringUtils.isEmpty(params.get("customerId"))) {
            customerId = Long.parseLong((String) params.get("customerId"));
        }
        String customerName = null;
        if (!StringUtils.isEmpty(params.get("customerName"))) {
            customerName = (String) params.get("customerName");
        }

        IPage<CustomerShareEntity> pageParams = new Query<CustomerShareEntity>().getPage(params);
        IPage<CustomerShareEntity> sharePage = customerShareDao.selectCustomerShares(pageParams, customerId, customerName);
        List<CustomerShareEntity> records = sharePage.getRecords();
        List<CustomerShareEntity> finalList = new ArrayList<>();
        for (CustomerShareEntity temp : records) {
            CustomerShareEntity customerShareWithImagesDto = customerShareDao.selectOneDetail(temp.getCustomerShareId());
            finalList.add(customerShareWithImagesDto);
        }
        sharePage.setRecords(finalList);
        return new PageUtils(sharePage);
    }

    @Override
    @Transactional
    public Boolean save(CustomerShareWithImagesDto customerShare) {
        customerShare.setShareTime(new Date());
        int count = customerShareDao.insert(customerShare);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(customerShare);
        return true;
    }

    @Override
    @Transactional
    public Boolean updateById(CustomerShareWithImagesDto customerShare) {
        CustomerShareEntity temp = customerShareDao.selectById(customerShare.getCustomerShareId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = customerShareDao.updateById(customerShare);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.saveOrUpdateBase(customerShare);
        return true;
    }

    @Override
    public CustomerShareWithImagesDto getDetail(Long id) {
        CustomerShareWithImagesDto customerShare = customerShareDao.selectOneDetail(id);
        if (customerShare == null || customerShare.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return customerShare;
    }

    @Override
    public PageUtils oneCustomerList(Map<String, Object> params, Long customerId) {
        CustomerEntity customerEntity = customerDao.selectById(customerId);
        if (customerEntity == null || customerEntity.getDeleteFlag().equals(1)) {
            //顾客不存在
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        IPage<CustomerShareEntity> pageParams = new Query<CustomerShareEntity>().getPage(params);
        IPage<CustomerShareEntity> sharePage = customerShareDao.selectCustomerShares(pageParams, customerId, null);
        return new PageUtils(sharePage);
    }

    public void saveOrUpdateBase(CustomerShareWithImagesDto customerShare) {
        customerShareImageDao.delete(new QueryWrapper<CustomerShareImageEntity>().eq("customer_share_id", customerShare.getCustomerShareId()));
        if (!CollectionUtils.isEmpty(customerShare.getCustomerShareImages())) {
            List<CustomerShareImageEntity> customerShareImages = new ArrayList<>();
            for (String image : customerShare.getCustomerShareImages()) {
                CustomerShareImageEntity customerShareImageEntity = new CustomerShareImageEntity();
                customerShareImageEntity.setCustomerShareId(customerShare.getCustomerShareId());
                customerShareImageEntity.setCustomerId(customerShare.getCustomerId());
                customerShareImageEntity.setCustomerShareImage(image);
                customerShareImages.add(customerShareImageEntity);
            }
            customerShareImageService.saveBatch(customerShareImages);
        }
    }

    @Override
    public Boolean removeByDeleteFlag(Long id) {
        CustomerShareEntity temp = customerShareDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = customerShareDao.deleteByDeleteFlag(id);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        return true;
    }

}