package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.constant.OpenVipTypeEnum;
import com.phoenix.fitness.common.utils.DateUtils;
import com.phoenix.fitness.modules.fitness.dao.CustomerAddHistoryDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerChargeHistoryDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerOpenVipHistoryDao;
import com.phoenix.fitness.modules.fitness.dto.AdminHomeToday;
import com.phoenix.fitness.modules.fitness.dto.CustomerCountDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerMoneyDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerAddHistoryEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerChargeHistoryEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerOpenVipHistoryEntity;
import com.phoenix.fitness.modules.fitness.service.StatisticsService;
import com.phoenix.fitness.modules.fitness.vo.PeriodVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("statisticsService")
@AllArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {

    private final CustomerAddHistoryDao customerAddHistoryDao;

    private final CustomerDao customerDao;

    private final CustomerChargeHistoryDao customerChargeHistoryDao;

    private final CustomerOpenVipHistoryDao customerOpenVipHistoryDao;

    @Override
    public AdminHomeToday getAdminToday() {
        String today= DateUtils.format(new Date());
        //潜在顾客添加数量
        Integer customerCount = customerAddHistoryDao.selectCount(new QueryWrapper<CustomerAddHistoryEntity>().eq("add_date", today));
        //新开通vip的顾客数量
        Integer vipCustomerCount = customerDao.selectCount(new QueryWrapper<CustomerEntity>().eq("open_vip_date", today));
        //销售金额
        List<CustomerChargeHistoryEntity> customerChargeHistoryList = customerChargeHistoryDao.selectList(new QueryWrapper<CustomerChargeHistoryEntity>().eq("charge_date", today));
        BigDecimal totalMoney=new BigDecimal("0");
        for(CustomerChargeHistoryEntity customerChargeHistoryEntity:customerChargeHistoryList){
            totalMoney=totalMoney.add(new BigDecimal(customerChargeHistoryEntity.getMoney()));
        }
        //会员卡销售数量
        Integer cardSaleCount = customerOpenVipHistoryDao.selectCount(new QueryWrapper<CustomerOpenVipHistoryEntity>().eq("open_vip_type", OpenVipTypeEnum.BUY_VIP_CARD.getStatusName()).eq("operation_date", today));
        AdminHomeToday adminHomeToday=new AdminHomeToday();
        adminHomeToday.setCustomerCount(customerCount);
        adminHomeToday.setVipCustomerCount(vipCustomerCount);
        adminHomeToday.setChargeMoney(totalMoney.toString());
        adminHomeToday.setCardSaleCount(cardSaleCount);
        return adminHomeToday;
    }

    @Override
    public List<CustomerCountDto> getCustomerCountPeriod(PeriodVO periodVO) {
        List<CustomerCountDto> customerCountDtos = customerAddHistoryDao.selectCustomerCountPeriod(DateUtils.format(periodVO.getStartDate()), DateUtils.format(periodVO.getEndDate()));
        List<Date> betweenDates = DateUtils.getBetweenDates(periodVO.getStartDate(), periodVO.getEndDate());
        List<CustomerCountDto> customerCountRealList=new ArrayList<>();
        for(Date date:betweenDates){
            CustomerCountDto customerCountDto=new CustomerCountDto();
            customerCountDto.setDetailDate(date);
            customerCountDto.setCustomerCount(0);
            for(CustomerCountDto temp:customerCountDtos){
                if(temp.getDetailDate().equals(date)){
                    customerCountDto.setCustomerCount(temp.getCustomerCount());
                    break;
                }
            }
            customerCountRealList.add(customerCountDto);
        }
        return customerCountRealList;
    }

    @Override
    public List<CustomerCountDto> getVipCustomerCountPeriod(PeriodVO periodVO) {
        List<CustomerCountDto> customerCountDtos = customerDao.selectVipCustomerCountPeriod(DateUtils.format(periodVO.getStartDate()), DateUtils.format(periodVO.getEndDate()));
        List<Date> betweenDates = DateUtils.getBetweenDates(periodVO.getStartDate(), periodVO.getEndDate());
        List<CustomerCountDto> customerCountRealList=new ArrayList<>();
        for(Date date:betweenDates){
            CustomerCountDto customerCountDto=new CustomerCountDto();
            customerCountDto.setDetailDate(date);
            customerCountDto.setCustomerCount(0);
            for(CustomerCountDto temp:customerCountDtos){
                if(temp.getDetailDate().equals(date)){
                    customerCountDto.setCustomerCount(temp.getCustomerCount());
                    break;
                }
            }
            customerCountRealList.add(customerCountDto);
        }
        return customerCountRealList;
    }

    @Override
    public List<CustomerMoneyDto> getCustomerMoneyPeriod(PeriodVO periodVO) {
        List<CustomerMoneyDto> customerMoneyDtos = customerChargeHistoryDao.selectCustomerMoneyPeriod(DateUtils.format(periodVO.getStartDate()), DateUtils.format(periodVO.getEndDate()));
        List<Date> betweenDates = DateUtils.getBetweenDates(periodVO.getStartDate(), periodVO.getEndDate());
        List<CustomerMoneyDto> customerMoneyRealList=new ArrayList<>();
        for(Date date:betweenDates){
            CustomerMoneyDto customerCountDto=new CustomerMoneyDto();
            customerCountDto.setDetailDate(date);
            customerCountDto.setCustomerMoney("0");
            for(CustomerMoneyDto temp:customerMoneyDtos){
                if(temp.getDetailDate().equals(date)){
                    customerCountDto.setCustomerMoney(temp.getCustomerMoney());
                    break;
                }
            }
            customerMoneyRealList.add(customerCountDto);
        }
        return customerMoneyRealList;
    }
}