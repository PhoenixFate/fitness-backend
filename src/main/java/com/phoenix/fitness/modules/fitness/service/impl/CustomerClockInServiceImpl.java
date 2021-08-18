package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.CustomerClockInDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerClockInEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerClockInService;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("customerClockInService")
@AllArgsConstructor
public class CustomerClockInServiceImpl extends ServiceImpl<CustomerClockInDao, CustomerClockInEntity> implements CustomerClockInService {

    private final CustomerClockInDao customerClockInDao;

    private final CustomerDao customerDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String customerName = "";
        if (!StringUtils.isEmpty(params.get("customerName"))) {
            customerName = (String) params.get("customerName");
        }
        Long customerId = null;
        if (!StringUtils.isEmpty(params.get("customerId"))) {
            customerId = Long.parseLong((String) params.get("customerId"));
        }
        IPage<CustomerClockInEntity> pageParams = new Query<CustomerClockInEntity>().getPage(params);
        IPage<CustomerClockInEntity> page = customerClockInDao.selectCustomerClockInList(pageParams, customerName, customerId);
        return new PageUtils(page);
    }

    @Override
    public Boolean clockIn(Long customerId) {
        CustomerEntity customer = customerDao.selectOne(new QueryWrapper<CustomerEntity>().eq("customer_id", customerId));
        if (customer == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        CustomerClockInEntity customerClockIn = new CustomerClockInEntity();
        customerClockIn.setCustomerId(customer.getCustomerId());
        customerClockIn.setCustomerNumber(customer.getCustomerNumber());
        customerClockIn.setPhysicalCardNumber(customer.getPhysicalCardNumber());
        customerClockIn.setClockInTime(new Date());
        customerClockInDao.insert(customerClockIn);
        return true;
    }

    @Override
    public Boolean clockOut(Long customerId) {
        List<CustomerClockInEntity> customerClockInEntityList = customerClockInDao.selectList(new QueryWrapper<CustomerClockInEntity>().eq("customer_id", customerId).orderByDesc("create_time"));
        if (CollectionUtils.isEmpty(customerClockInEntityList)) {
            throw new FitnessException(ExceptionEnum.CLOCK_IN_EMPTY);
        }
        //修改最近一条的退卡时间
        customerClockInEntityList.get(0).setClockOutTime(new Date());
        customerClockInDao.updateById(customerClockInEntityList.get(0));
        return true;
    }

}