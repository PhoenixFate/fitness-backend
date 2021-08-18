package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.PeriodUtil;
import com.phoenix.fitness.modules.admin.dto.PeriodCountDto;
import com.phoenix.fitness.modules.admin.dto.PeriodDto;
import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;
import com.phoenix.fitness.modules.fitness.dao.CustomerContractDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerReturnVisitDao;
import com.phoenix.fitness.modules.fitness.dao.VisitorDao;
import com.phoenix.fitness.modules.fitness.dto.CustomerContractWithLogDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerReturnVisitEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerMarketingService;

import java.util.ArrayList;
import java.util.List;


@Service("customerMarketingService")
@AllArgsConstructor
public class CustomerMarketingServiceImpl extends ServiceImpl<CustomerDao, CustomerEntity> implements CustomerMarketingService {

    private final CustomerDao customerDao;

    private final CustomerContractDao customerContractDao;

    private final CustomerReturnVisitDao customerReturnVisitDao;

    private final VisitorDao visitorDao;

    @Override
    public PageUtils queryCardPage(CustomerSearchForm customerSearchForm) {
        Page<CustomerEntity> pageParams = new Page<>(customerSearchForm.getPage(), customerSearchForm.getLimit());
        IPage<CustomerEntity> customerPage = customerDao.selectCustomerMarketCardPage(pageParams, customerSearchForm);
        List<CustomerEntity> records = customerPage.getRecords();
        for (CustomerEntity customerEntity : records) {
            List<CustomerContractEntity> customerContractEntityList = customerContractDao.selectCustomerContractsSpecial(customerEntity.getCustomerId());
            List<CustomerReturnVisitEntity> customerReturnVisitEntityList = customerReturnVisitDao.selectList(new QueryWrapper<CustomerReturnVisitEntity>().eq("customer_id", customerEntity.getCustomerId()).orderByDesc("create_time"));
            customerEntity.setContracts(customerContractEntityList);
            customerEntity.setCustomerReturnVisits(customerReturnVisitEntityList);
        }
        return new PageUtils(customerPage);
    }

    @Override
    public PageUtils queryTrainingPage(CustomerSearchForm customerSearchForm) {
        Page<CustomerContractWithLogDto> pageParams = new Page<>(customerSearchForm.getPage(), customerSearchForm.getLimit());
        IPage<CustomerContractWithLogDto> customerPage = customerContractDao.selectCustomerMarketTrainingPage(pageParams, customerSearchForm);
        List<CustomerContractWithLogDto> records = customerPage.getRecords();
        for (CustomerContractWithLogDto contract : records) {
            List<CustomerReturnVisitEntity> customerReturnVisitEntityList = customerReturnVisitDao.selectList(new QueryWrapper<CustomerReturnVisitEntity>().eq("customer_id", contract.getCustomerId()).orderByDesc("create_time"));
            contract.setCustomerReturnVisits(customerReturnVisitEntityList);
        }
        return new PageUtils(customerPage);
    }

    @Override
    public List<PeriodDto> periodCount() {
        List<PeriodDto> list = new ArrayList<>();
        List<PeriodCountDto> customerPeriodCounts = visitorDao.selectCustomerPeriodCount();
        PeriodDto customerPeriod = new PeriodDto();
        customerPeriod.setTypeName("顾客");
        PeriodUtil.setPeriodCount(customerPeriodCounts, customerPeriod);

        List<PeriodCountDto> visitorPeriodCounts = visitorDao.selectVisitorPeriodCount();
        PeriodDto visitorPeriod = new PeriodDto();
        visitorPeriod.setTypeName("访客");
        PeriodUtil.setPeriodCount(visitorPeriodCounts, visitorPeriod);

        list.add(customerPeriod);
        list.add(visitorPeriod);
        return list;
    }

    @Override
    public List<List<Integer>> periodCountChart() {
        List<PeriodDto> periods = this.periodCount();
        List<List<Integer>> data = new ArrayList<>();
        for (PeriodDto periodDto : periods) {
            List<Integer> temp = new ArrayList<>();
            temp.add(periodDto.getOneToFive());
            temp.add(periodDto.getFiveToEight());
            temp.add(periodDto.getEightToNine());
            temp.add(periodDto.getNineToTen());
            temp.add(periodDto.getTenToEleven());
            temp.add(periodDto.getElevenToTwelve());
            temp.add(periodDto.getTwelveToThirteen());
            temp.add(periodDto.getThirteenToFourteen());
            temp.add(periodDto.getFourteenToFifteen());
            temp.add(periodDto.getFifteenToSixteen());
            temp.add(periodDto.getSixteenToSeventeen());
            temp.add(periodDto.getSeventeenToEighteen());
            temp.add(periodDto.getEighteenToNineteen());
            temp.add(periodDto.getNineteenToTwenty());
            temp.add(periodDto.getTwentyTo21());
            temp.add(periodDto.getTwentyOneTo22());
            temp.add(periodDto.getTwentyTwoTo23());
            temp.add(periodDto.getTwentyThreeTo01());
            data.add(temp);
        }
        return data;
    }


}