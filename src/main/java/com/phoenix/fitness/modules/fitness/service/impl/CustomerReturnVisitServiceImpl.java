package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.DateUtils;
import com.phoenix.fitness.modules.fitness.dao.CustomerDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerReturnVisitDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerReturnVisitEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerReturnVisitService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("customerReturnVisitService")
@AllArgsConstructor
public class CustomerReturnVisitServiceImpl extends ServiceImpl<CustomerReturnVisitDao, CustomerReturnVisitEntity> implements CustomerReturnVisitService {

    private final CustomerReturnVisitDao customerReturnVisitDao;

    private final CustomerDao customerDao;

    @Override
    public List<CustomerReturnVisitEntity> getOneCustomerAllReturnVisit(Long customerId) {
        CustomerEntity customerEntity = customerDao.selectById(customerId);
        if (customerEntity == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        return customerReturnVisitDao.selectList(new QueryWrapper<CustomerReturnVisitEntity>().eq("customer_id", customerId));
    }

    @Override
    public void saveCustomerReturnVisit(CustomerReturnVisitEntity customerReturnVisitEntity) {
        CustomerEntity customerEntity = customerDao.selectById(customerReturnVisitEntity.getCustomerId());
        if (customerEntity == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        customerReturnVisitDao.insert(customerReturnVisitEntity);
    }

    @Override
    public List<CustomerReturnVisitEntity> remind(Date date) {
        if (date == null) {
            date = new Date();
        }
        Date endDate = DateUtils.addDateDays(date, 3);
        List<CustomerReturnVisitEntity> finalList = new ArrayList<>();
        List<Long> customerIds = customerReturnVisitDao.selectCustomerIds();
        for (Long customerId : customerIds) {
            List<CustomerReturnVisitEntity> tempList = customerReturnVisitDao.selectList(new QueryWrapper<CustomerReturnVisitEntity>().eq("customer_id", customerId).orderByDesc("create_time"));
            if (!CollectionUtils.isEmpty(tempList)) {
                //判断最新的一条回访的下次回访时间是否在时间范围内
                CustomerReturnVisitEntity customerReturnVisitEntity = tempList.get(0);
                if (customerReturnVisitEntity.getNextVisitDate() != null) {
                    if (customerReturnVisitEntity.getNextVisitDate().getTime() >= date.getTime() && customerReturnVisitEntity.getNextVisitDate().getTime() < endDate.getTime()) {
                        finalList.add(customerReturnVisitEntity);
                    }
                }
            }
        }
        return finalList;
    }
}