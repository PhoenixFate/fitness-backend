package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.constant.*;
import com.phoenix.fitness.common.utils.*;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.dto.*;
import com.phoenix.fitness.modules.fitness.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.*;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.*;
import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.dto.*;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanDayService;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanPeriodService;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanWeekService;
import com.phoenix.fitness.modules.fitness.service.CustomerService;
import com.phoenix.fitness.modules.fitness.vo.CoachChangeForm;
import com.phoenix.fitness.modules.fitness.vo.CustomerPeriodVO;
import com.phoenix.fitness.modules.fitness.vo.WeChatLoginForm;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.phoenix.fitness.common.constant.ExceptionEnum.MOBILE_DUPLICATE;


@Service("customerService")
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl extends ServiceImpl<CustomerDao, CustomerEntity> implements CustomerService {

    private final CustomerDao customerDao;

    private final BodyTestDataDao bodyTestDataDao;

    private final BodyStatusDataDao bodyStatusDataDao;

    private final CustomerPlanDao customerPlanDao;

    private final ClassInfoDao classInfoDao;

    private final CustomerPlanPeriodDao customerPlanPeriodDao;

    private final CustomerPlanPeriodService customerPlanPeriodService;

    private final CustomerPlanWeekDao customerPlanWeekDao;

    private final CustomerPlanWeekService customerPlanWeekService;

    private final CustomerPlanDayDao customerPlanDayDao;

    private final CustomerPlanDayService customerPlanDayService;

    private final CustomerAddHistoryDao customerAddHistoryDao;

    private final CustomerOpenVipHistoryDao customerOpenVipHistoryDao;

    private final CoachDao coachDao;

    private final CustomerDietPlanDao customerDietPlanDao;

    private final DietPlanDao dietPlanDao;

    private final CustomerClockInDao customerClockInDao;

    private final CustomerContractDao customerContractDao;

    @Value("${fitness.customer.initialNumber}")
    private Long customerInitialNumber;


    @Override
    public PageUtils queryPage(CustomerSearchForm customerSearchForm, UserEntity user) {
        Page<CustomerEntity> pageParams = new Page<>(customerSearchForm.getPage(), customerSearchForm.getLimit());
        IPage<CustomerEntity> customerPage = customerDao.selectCustomerPage(pageParams, customerSearchForm, user);
        return new PageUtils(customerPage);
    }

    @Override
    public PageUtils notVipList(Map<String, Object> params, UserEntity user) {
        QueryWrapper<CustomerEntity> customerEntityQueryWrapper = new QueryWrapper<>();
        customerEntityQueryWrapper.eq("is_vip", 0).eq("delete_flag", 0);
        if (user != null) {
            if (user.getGymId() != null) {
                customerEntityQueryWrapper.eq("gym_id", user.getGymId());
            }
            if (user.getPartnerId() != null) {
                customerEntityQueryWrapper.eq("partner_id", user.getPartnerId());
            }
        }
        IPage<CustomerEntity> page = this.page(
                new Query<CustomerEntity>().getPage(params),
                customerEntityQueryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public Boolean updateCommon(CustomerEntity customer) {
        //检查physicalCardIndex,physicalCardNumber, 只能绑定一次
        if (!StringUtils.isEmpty(customer.getPhysicalCardIndex())) {
            List<CustomerEntity> tempCustomerList = customerDao.selectList(new QueryWrapper<CustomerEntity>().eq("physical_card_index", customer.getPhysicalCardIndex()).ne("customer_id", customer.getCustomerId()));
            if (!CollectionUtils.isEmpty(tempCustomerList)) {
                throw new FitnessException(ExceptionEnum.PHYSICAL_CARD_INDEX_ERROR);
            }
        }
        if (!StringUtils.isEmpty(customer.getPhysicalCardNumber())) {
            List<CustomerEntity> tempCustomerList = customerDao.selectList(new QueryWrapper<CustomerEntity>().eq("physical_card_number", customer.getPhysicalCardNumber()).ne("customer_id", customer.getCustomerId()));
            if (!CollectionUtils.isEmpty(tempCustomerList)) {
                throw new FitnessException(ExceptionEnum.PHYSICAL_CARD_NUMBER_ERROR);
            }
        }
        this.updateNameIndex(customer);
        return customerDao.updateById(customer) > 0;
    }

    public void updateNameIndex(CustomerEntity customer) {
        //设置nameIndex
        if (!StringUtils.isEmpty(customer.getName())) {
            char firstChar = customer.getName().charAt(0);
            if (ChinesePinyinUtil.isChinese(firstChar)) {
                customer.setNameIndex((ChinesePinyinUtil.getPingYin(firstChar + "").charAt(0) + "").toUpperCase());
            } else if (ChinesePinyinUtil.isEnglish(firstChar + "")) {
                customer.setNameIndex((firstChar + "").toUpperCase());
            } else {
                customer.setNameIndex("#");
            }
        }
    }

    @Override
    public CustomerEntity getInfoByNumber(Long customerNumber) {
        return customerDao.selectOne(new QueryWrapper<CustomerEntity>().eq("customer_number", customerNumber));
    }

    @Override
    public CustomerEntity getInfoByMobile(String mobile) {
        List<CustomerEntity> customerList = customerDao.selectList(new QueryWrapper<CustomerEntity>().eq("mobile", mobile));
        if (!CollectionUtils.isEmpty(customerList)) {
            return customerList.get(0);
        }
        return null;
    }

    @Override
    public CustomerEntity weChatLogin(WeChatLoginForm weChatLoginForm) {
        List<CustomerEntity> customerList = customerDao.selectList(new QueryWrapper<CustomerEntity>().eq("mobile", weChatLoginForm.getMobile()));
        CustomerEntity customer;
        if (customerList.size() > 0) {
            customer = customerList.get(0);
        } else {
            return null;
        }
        if (!StringUtils.isEmpty(weChatLoginForm.getAvatarUrl())) {
            if (StringUtils.isEmpty(customer.getAvatar())) {
                //为空才修改头像，否则例如会有admin修改顾客头像的情况，则不修改
                customer.setAvatar(weChatLoginForm.getAvatarUrl());
            }
        }
        if (weChatLoginForm.getGender() != null) {
            customer.setGender(weChatLoginForm.getGender());
        }
        if (!StringUtils.isEmpty(weChatLoginForm.getNickName())) {
            customer.setNickname(weChatLoginForm.getNickName());
        }
        if (!StringUtils.isEmpty(weChatLoginForm.getOpenId())) {
            customer.setOpenId(weChatLoginForm.getOpenId());
        }
        try {
            customerDao.updateById(customer);
        } catch (Exception e) {
        }
        return customer;
    }

    @Override
    public CustomerEntity saveCustomerSpecial(CustomerEntity customer, UserEntity user) {
        customer.setIsVip(0);
        customer.setProgressIndex(1);
        if (user != null) {
            if (user.getGymId() != null) {
                customer.setGymId(user.getGymId());
            }
            if (user.getPartnerId() != null) {
                customer.setPartnerId(user.getPartnerId());
            }
        }

        //检查physicalCardIndex,physicalCardNumber, 只能绑定一次
        if (!StringUtils.isEmpty(customer.getPhysicalCardIndex())) {
            List<CustomerEntity> tempCustomerList = customerDao.selectList(new QueryWrapper<CustomerEntity>().eq("physical_card_index", customer.getPhysicalCardIndex()));
            if (!CollectionUtils.isEmpty(tempCustomerList)) {
                throw new FitnessException(ExceptionEnum.PHYSICAL_CARD_INDEX_ERROR);
            }
        }
        if (!StringUtils.isEmpty(customer.getPhysicalCardNumber())) {
            List<CustomerEntity> tempCustomerList = customerDao.selectList(new QueryWrapper<CustomerEntity>().eq("physical_card_number", customer.getPhysicalCardNumber()));
            if (!CollectionUtils.isEmpty(tempCustomerList)) {
                throw new FitnessException(ExceptionEnum.PHYSICAL_CARD_NUMBER_ERROR);
            }
        }


        //设置顾客编号，为之前的最大顾客编号+1
        List<CustomerEntity> customerEntityList = customerDao.selectList(new QueryWrapper<CustomerEntity>().orderByDesc("customer_number"));
        if (customerEntityList.size() > 0) {
            if (customerEntityList.get(0).getCustomerNumber() != null) {
                customer.setCustomerNumber(customerEntityList.get(0).getCustomerNumber() + 1);
            } else {
                customer.setCustomerNumber(customerInitialNumber + 1);
            }
        } else {
            customer.setCustomerNumber(customerInitialNumber + 1);
        }

        //设置nameIndex
        this.updateNameIndex(customer);
        CustomerEntity samePhone =
                customerDao.selectOne(new QueryWrapper<CustomerEntity>()
                        .eq("mobile", customer.getMobile())
                        .eq("gym_id", customer.getGymId())
                );
        if (samePhone != null && samePhone.getCustomerId() != null) {
            throw new FitnessException(MOBILE_DUPLICATE);
        }
        customer.setProgressIndex(1);
        customerDao.insert(customer);

        //设置bodyTest默认值
        BodyTestDataEntity bodyTestDataEntity = new BodyTestDataEntity();
        bodyTestDataEntity.setCustomerId(customer.getCustomerId());
        bodyTestDataEntity.setIsLatest(1);
        bodyTestDataDao.insert(bodyTestDataEntity);

        //设置bodyStatus默认值
        BodyStatusDataEntity bodyStatusDataEntity = new BodyStatusDataEntity();
        bodyStatusDataEntity.setCustomerId(customer.getCustomerId());
        bodyStatusDataEntity.setIsLatest(1);
        bodyStatusDataDao.insert(bodyStatusDataEntity);

        //设置customerPlan默认值
        CustomerPlanEntity customerPlanEntity = new CustomerPlanEntity();
        customerPlanEntity.setCustomerId(customer.getCustomerId());
        customerPlanEntity.setIsLatest(1);
        customerPlanEntity.setPlanStatus(0);
        customerPlanDao.insert(customerPlanEntity);

        //在历史表中添加一条记录数据
        CustomerAddHistoryEntity customerAddHistoryEntity = new CustomerAddHistoryEntity();
        customerAddHistoryEntity.setCoachId(customer.getCoachId());
        customerAddHistoryEntity.setCustomerId(customer.getCustomerId());
        customerAddHistoryEntity.setAddDate(new Date());
        customerAddHistoryEntity.setPeriod(PeriodUtil.getPeriod(new Date()));
        customerAddHistoryDao.insert(customerAddHistoryEntity);
        return customer;
    }

    @Override
    public CustomerEntity getDetail(Long id) {
        CustomerEntity customerEntity = customerDao.selectCustomerWithDetail((Long) id);
        List<ClassInfoWithExercisesDto> classInfoEntityList = classInfoDao.selectClassInfoListWithDetail(new UserEntity());

        CustomerPlanWithAllDto customerPlan = customerEntity.getCustomerPlan();
        if ((customerPlan != null) && (customerPlan.getTrainingPlanId() != null)) {
            //添加课程信息
            for (CustomerPlanPeriodEntity customerPlanPeriodEntity : customerPlan.getCustomerPlanPeriods()) {
                if (!CollectionUtils.isEmpty(customerPlanPeriodEntity.getCustomerPlanWeeks())) {
                    for (CustomerPlanWeekEntity customerPlanWeekEntity : customerPlanPeriodEntity.getCustomerPlanWeeks()) {
                        if (!CollectionUtils.isEmpty(customerPlanWeekEntity.getCustomerPlanDays())) {
                            for (CustomerPlanDayWithClassInfoDto customerPlanDayEntity : customerPlanWeekEntity.getCustomerPlanDays()) {
                                for (ClassInfoWithExercisesDto classInfoEntity : classInfoEntityList) {
                                    if (classInfoEntity.getClassId().equals(customerPlanDayEntity.getClassId())) {
                                        customerPlanDayEntity.setClassInfo(classInfoEntity);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (customerEntity.getCustomerDietPlan() != null) {
            DietPlanWithItemsDto dietPlan = dietPlanDao.selectDietPlanWithDetail(customerEntity.getCustomerDietPlan().getDietPlanId());
            customerEntity.getCustomerDietPlan().setDietPlan(dietPlan);
        }
        return customerEntity;
    }


    @Override
    public Boolean updateById(CustomerEntity customer, UserEntity user) {
        //这个主要是非vip会员的信息保存
        if (customer.getProgressIndex() == 5 && customer.getIsVip().equals(0)) {
            //升级成vip
            customer.setIsVip(1);
            customer.setOpenVipDate(new Date());
            customer.getCustomerPlan().setPlanStatus(1);
            //vip的有效时间由支付之后开始计算
            customer.setVipStartDate(null);
            customer.setVipEndDate(null);
            List<CustomerOpenVipHistoryEntity> customerOpenVipHistoryEntityList = customerOpenVipHistoryDao.selectList(new QueryWrapper<CustomerOpenVipHistoryEntity>().eq("customer_id", customer.getCustomerId()));
            if (customerOpenVipHistoryEntityList == null || customerOpenVipHistoryEntityList.size() == 0) {
                //添加手动开通vip记录
                CustomerOpenVipHistoryEntity customerOpenVipHistory = new CustomerOpenVipHistoryEntity();
                customerOpenVipHistory.setCustomerId(customer.getCustomerId());
                customerOpenVipHistory.setCoachId(customer.getCoachId());
                customerOpenVipHistory.setOpenVipType(OpenVipTypeEnum.MANUAL.getStatusName());
                customerOpenVipHistory.setOperationDate(new Date());
                customerOpenVipHistoryDao.insert(customerOpenVipHistory);
            }
        }
        if (customer.getCustomerPlan() == null) {
            customer.setTotalClass(0);
        }

        customerDao.updateById(customer);
        bodyTestDataDao.updateById(customer.getBodyTestData());
        bodyStatusDataDao.updateById(customer.getBodyStatusData());

        if (customer.getCustomerPlan() != null && customer.getCustomerPlan().getCoachId() == null) {
            //默认当前训练计划的辅佐教练为添加当前顾客的教练
            customer.getCustomerPlan().setCoachId(customer.getCoachId());
        }
        customerPlanDao.updateById(customer.getCustomerPlan());
        CustomerPlanEntity customerPlan = customer.getCustomerPlan();
        if (customer.getCustomerPlan().getTrainingPlanId() == null || customer.getCustomerPlan().getTrainingPlan() == null) {
            //无计划、删除当前训练计划
            customerPlanPeriodDao.delete(new QueryWrapper<CustomerPlanPeriodEntity>().eq("customer_plan_id", customerPlan.getCustomerPlanId()));
            customerPlanWeekDao.delete(new QueryWrapper<CustomerPlanWeekEntity>().eq("customer_plan_id", customerPlan.getCustomerPlanId()));
            customerPlanDayDao.delete(new QueryWrapper<CustomerPlanDayEntity>().eq("customer_plan_id", customerPlan.getCustomerPlanId()));
        } else {
            //有训练计划
            List<CustomerPlanPeriodEntity> customerPlanPeriods = customer.getCustomerPlan().getCustomerPlanPeriods();
            List<Long> periodIds = new ArrayList<>();
            for (CustomerPlanPeriodEntity customerPlanPeriodEntity : customerPlanPeriods) {
                customerPlanPeriodEntity.setCustomerPlanId(customerPlan.getCustomerPlanId());
                customerPlanPeriodEntity.setPeriodIndex(customerPlanPeriods.indexOf(customerPlanPeriodEntity) + 1);
                customerPlanPeriodEntity.setCustomerId(customer.getCustomerId());
                if (customerPlanPeriodEntity.getCustomerPlanPeriodId() != null) {
                    periodIds.add(customerPlanPeriodEntity.getCustomerPlanPeriodId());
                }
            }
            if (periodIds.size() > 0) {
                customerPlanPeriodDao.delete(new QueryWrapper<CustomerPlanPeriodEntity>().notIn("customer_plan_period_id", periodIds).eq("customer_plan_id", customerPlan.getCustomerPlanId()));
            } else {
                customerPlanPeriodDao.delete(new QueryWrapper<CustomerPlanPeriodEntity>().eq("customer_plan_id", customerPlan.getCustomerPlanId()));
            }
            customerPlanPeriodService.saveOrUpdateBatch(customerPlanPeriods);

            List<Long> weekIds = new ArrayList<>();
            List<CustomerPlanWeekEntity> customerPlanWeekEntities = new ArrayList<>();
            for (CustomerPlanPeriodEntity customerPlanPeriodEntity : customerPlanPeriods) {
                if (!CollectionUtils.isEmpty(customerPlanPeriodEntity.getCustomerPlanWeeks())) {
                    for (CustomerPlanWeekEntity customerPlanWeekEntity : customerPlanPeriodEntity.getCustomerPlanWeeks()) {
                        customerPlanWeekEntity.setCustomerPlanId(customerPlan.getCustomerPlanId());
                        customerPlanWeekEntity.setCustomerPlanPeriodId(customerPlanPeriodEntity.getCustomerPlanPeriodId());
                        customerPlanWeekEntity.setWeekIndex(customerPlanPeriodEntity.getCustomerPlanWeeks().indexOf(customerPlanWeekEntity) + 1);
                        customerPlanWeekEntities.add(customerPlanWeekEntity);
                        if (customerPlanWeekEntity.getCustomerPlanWeekId() != null) {
                            weekIds.add(customerPlanWeekEntity.getCustomerPlanWeekId());
                        }
                    }
                }
            }
            if (weekIds.size() > 0) {
                customerPlanWeekDao.delete(new QueryWrapper<CustomerPlanWeekEntity>().notIn("customer_plan_week_id", weekIds).eq("customer_plan_id", customerPlan.getCustomerPlanId()));
                customerPlanDayDao.delete(new QueryWrapper<CustomerPlanDayEntity>().notIn("customer_plan_week_id", weekIds).eq("customer_plan_id", customerPlan.getCustomerPlanId()));
            } else {
                customerPlanWeekDao.delete(new QueryWrapper<CustomerPlanWeekEntity>().eq("customer_plan_id", customerPlan.getCustomerPlanId()));
                customerPlanDayDao.delete(new QueryWrapper<CustomerPlanDayEntity>().eq("customer_plan_id", customerPlan.getCustomerPlanId()));
            }
            customerPlanWeekService.saveOrUpdateBatch(customerPlanWeekEntities);

            List<CustomerPlanDayEntity> customerPlanDayEntities = new ArrayList<>();
            Integer totalClassNumber = 0;
            Integer totalCompletedClassNumber = 0;
            List<Long> dayIds = new ArrayList<>();
            for (CustomerPlanPeriodEntity customerPlanPeriodEntity : customerPlanPeriods) {
                if (!CollectionUtils.isEmpty(customerPlanPeriodEntity.getCustomerPlanWeeks())) {
                    for (CustomerPlanWeekEntity customerPlanWeekEntity : customerPlanPeriodEntity.getCustomerPlanWeeks()) {
                        for (CustomerPlanDayEntity customerPlanDayEntity : customerPlanWeekEntity.getCustomerPlanDays()) {
                            customerPlanDayEntity.setCustomerId(customer.getCustomerId());
                            customerPlanDayEntity.setCustomerPlanId(customerPlan.getCustomerPlanId());
                            customerPlanDayEntity.setCustomerPlanPeriodId(customerPlanPeriodEntity.getCustomerPlanPeriodId());
                            customerPlanDayEntity.setCustomerPlanWeekId(customerPlanWeekEntity.getCustomerPlanWeekId());
                            if (customerPlanDayEntity.getCoachId() == null) {
                                customerPlanDayEntity.setCoachId(customer.getCustomerPlan().getCoachId());
                            }
                            if (customerPlanDayEntity.getStartTime() == null) {
                                customerPlanDayEntity.setStartTime(customer.getCustomerPlan().getStartTime());
                            }
                            if (customerPlanDayEntity.getEndTime() == null) {
                                customerPlanDayEntity.setEndTime(customer.getCustomerPlan().getEndTime());
                            }
                            if (user != null) {
                                if (user.getGymId() != null) {
                                    customerPlanDayEntity.setGymId(user.getGymId());
                                }
                                if (user.getPartnerId() != null) {
                                    customerPlanDayEntity.setPartnerId(user.getPartnerId());
                                }
                            }
                            if (customer.getProgressIndex() == 5 && new Integer("1").equals(customerPlanDayEntity.getIsActive())) {
                                //开通vip，设置isPay为1
                                customerPlanDayEntity.setIsPay(1);
                            }
                            if (customerPlanDayEntity.getIsRest() != null) {
                                customerPlanDayEntities.add(customerPlanDayEntity);
                            }
                            if (customerPlanDayEntity.getClassId() != null) {
                                totalClassNumber += 1;
                            }
                            if (new Integer(1).equals(customerPlanDayEntity.getStatus())) {
                                //已完成
                                totalCompletedClassNumber += 1;
                            }
                            if (customerPlanDayEntity.getCustomerPlanDayId() != null) {
                                dayIds.add(customerPlanDayEntity.getCustomerPlanDayId());
                            }
                        }
                    }
                }
            }
            if (dayIds.size() > 0) {
                customerPlanDayDao.delete(new QueryWrapper<CustomerPlanDayEntity>().notIn("customer_plan_day_id", dayIds).eq("customer_plan_id", customerPlan.getCustomerPlanId()));
            } else {
                customerPlanDayDao.delete(new QueryWrapper<CustomerPlanDayEntity>().eq("customer_plan_id", customerPlan.getCustomerPlanId()));
            }
            customer.setTotalClass(totalClassNumber);
            customer.setCurrentClass(totalCompletedClassNumber);
            customerDao.updateById(customer);
            customerPlanDayService.saveOrUpdateBatch(customerPlanDayEntities);
        }
        return true;
    }

    @Override
    public List<BodyTestDataEntity> getBodyTestList(Long customerId) {
        return bodyTestDataDao.selectList(new QueryWrapper<BodyTestDataEntity>().eq("customer_id", customerId).orderByDesc("add_time"));
    }

    @Override
    @Transactional
    public void addBodyTest(BodyTestDataEntity bodyTest) {
        // 将以前的最新的一条改为不是最新
        BodyTestDataEntity temp = bodyTestDataDao.selectOne(new QueryWrapper<BodyTestDataEntity>().eq("is_latest", 1).eq("customer_id", bodyTest.getCustomerId()));
        if (temp != null) {
            temp.setIsLatest(0);
            bodyTestDataDao.updateById(temp);
        }
        //保存最新数据
        bodyTest.setIsLatest(1);
        bodyTestDataDao.insert(bodyTest);
    }

    @Override
    public void updateBodyTest(BodyTestDataEntity bodyTest) {
        bodyTestDataDao.updateById(bodyTest);
    }

    @Override
    public List<BodyStatusDataEntity> getBodyStatusList(Long customerId) {
        return bodyStatusDataDao.selectList(new QueryWrapper<BodyStatusDataEntity>().eq("customer_id", customerId).orderByDesc("add_time"));
    }

    @Override
    public void addBodyStatus(BodyStatusDataEntity bodyStatus) {
        // 将以前的最新的一条改为不是最新
        BodyStatusDataEntity temp = bodyStatusDataDao.selectOne(new QueryWrapper<BodyStatusDataEntity>().eq("is_latest", 1).eq("customer_id", bodyStatus.getCustomerId()));
        if (temp != null) {
            temp.setIsLatest(0);
            bodyStatusDataDao.updateById(temp);
        }
        //保存最新数据
        bodyStatus.setIsLatest(1);
        bodyStatusDataDao.insert(bodyStatus);
    }

    @Override
    public void updateBodyStatus(BodyStatusDataEntity bodyStatus) {
        bodyStatusDataDao.updateById(bodyStatus);
    }

    @Override
    public List<CustomerDietPlanEntity> getDietPlanList(Long customerId) {
        List<CustomerDietPlanEntity> customerDietPlanEntities = customerDietPlanDao.selectList(new QueryWrapper<CustomerDietPlanEntity>().eq("customer_id", customerId).orderByDesc("add_time"));
        for (CustomerDietPlanEntity customerDietPlanEntity : customerDietPlanEntities) {
            DietPlanWithItemsDto dietPlanWithItemsDto = dietPlanDao.selectDietPlanWithDetail(customerDietPlanEntity.getDietPlanId());
            customerDietPlanEntity.setDietPlan(dietPlanWithItemsDto);
        }
        return customerDietPlanEntities;
    }

    @Override
    public void addDietPlan(CustomerDietPlanEntity customerDietPlanEntity) {
        // 将以前的最新的一条改为不是最新
        CustomerDietPlanEntity temp = customerDietPlanDao.selectOne(new QueryWrapper<CustomerDietPlanEntity>().eq("is_latest", 1).eq("customer_id", customerDietPlanEntity.getCustomerId()));
        if (temp != null) {
            temp.setIsLatest(0);
            customerDietPlanDao.updateById(temp);
        }
        //保存最新数据
        customerDietPlanEntity.setIsLatest(1);
        customerDietPlanDao.insert(customerDietPlanEntity);
    }

    @Override
    public void updateDietPlan(CustomerDietPlanEntity customerDietPlanEntity) {
        customerDietPlanDao.updateById(customerDietPlanEntity);
    }

    @Override
    public List<CustomerPlanWithAllDto> getCustomerPlanList(Long customerId) {
        List<CustomerPlanWithAllDto> customerPlanList = customerPlanDao.selectCustomerPlanList(customerId);
        List<ClassInfoWithExercisesDto> classInfoEntityList = classInfoDao.selectClassInfoListWithDetail(new UserEntity());

        //补全课程信息
        for (CustomerPlanWithAllDto customerPlan : customerPlanList) {
            if ((customerPlan != null) && (customerPlan.getTrainingPlanId() != null)) {
                //添加课程信息
                for (CustomerPlanPeriodEntity customerPlanPeriodEntity : customerPlan.getCustomerPlanPeriods()) {
                    if (!CollectionUtils.isEmpty(customerPlanPeriodEntity.getCustomerPlanWeeks())) {
                        for (CustomerPlanWeekEntity customerPlanWeekEntity : customerPlanPeriodEntity.getCustomerPlanWeeks()) {
                            if (!CollectionUtils.isEmpty(customerPlanWeekEntity.getCustomerPlanDays())) {
                                for (CustomerPlanDayWithClassInfoDto customerPlanDayEntity : customerPlanWeekEntity.getCustomerPlanDays()) {
                                    for (ClassInfoWithExercisesDto classInfoEntity : classInfoEntityList) {
                                        if (classInfoEntity.getClassId().equals(customerPlanDayEntity.getClassId())) {
                                            customerPlanDayEntity.setClassInfo(classInfoEntity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return customerPlanList;
    }

    @Override
    public List<BodyTestDataEntity> bodyTestList(CustomerPeriodVO periodVO) {
        return bodyTestDataDao.selectList(new QueryWrapper<BodyTestDataEntity>().eq("customer_id", periodVO.getCustomerId()).gt("add_time", periodVO.getStartDate()).lt("add_time", DateUtils.addDateDays(periodVO.getEndDate(), 1)));
    }

    @Override
    public Boolean changeCoach(CoachChangeForm coachChangeForm) {
        CustomerEntity customerEntity = customerDao.selectById(coachChangeForm.getCustomerId());
        if (customerEntity == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        if (customerEntity.getCoachId().equals(coachChangeForm.getCoachId())) {
            throw new FitnessException(ExceptionEnum.COACH_EQUAL_BEFORE);
        }
        CoachEntity coachEntity = coachDao.selectById(coachChangeForm.getCoachId());
        if (coachEntity == null) {
            throw new FitnessException(ExceptionEnum.COACH_NOT_FOUND);
        }
        customerEntity.setCoachId(coachChangeForm.getCoachId());
        customerDao.updateById(customerEntity);
        return true;
    }

    @Override
    public CustomerInPhysicalCardDto getInfoByPhysicalCard(String physicalCard) {
        CustomerEntity customer = customerDao.selectOne(new QueryWrapper<CustomerEntity>().eq("physical_card_number", physicalCard));
        if (customer == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_PHYSICAL_NOT_FOUND);
        }
        CustomerInPhysicalCardDto customerInPhysicalCardDto = new CustomerInPhysicalCardDto();
        customerInPhysicalCardDto.setCustomer(customer);

        List<CustomerContractEntity> customerContractEntityList = customerContractDao.selectCustomerContracts(customer.getCustomerId());
        customerInPhysicalCardDto.setCustomerContractList(customerContractEntityList);

        List<CustomerClockInEntity> customerClockInList = customerClockInDao.selectList(new QueryWrapper<CustomerClockInEntity>().eq("customer_id", customer.getCustomerId()).orderByDesc("create_time"));
        if (!CollectionUtils.isEmpty(customerClockInList)) {
            customerInPhysicalCardDto.setCustomerClockIn(customerClockInList.get(0));
        }
        return customerInPhysicalCardDto;
    }

    @Override
    public void stopCard(Long customerId) {
        CustomerEntity customerEntity = customerDao.selectById(customerId);
        if (customerEntity == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        customerEntity.setStatus(CustomerStatusEnum.STOP_CARD.getCustomerStatusName());
        customerDao.updateById(customerEntity);
    }

    @Override
    public void cancel(Long customerId) {
        CustomerEntity customerEntity = customerDao.selectById(customerId);
        if (customerEntity == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        customerEntity.setStatus(CustomerStatusEnum.CANCELLED.getCustomerStatusName());
        customerDao.updateById(customerEntity);
    }

    @Override
    public void cancelStop(Long customerId) {
        CustomerEntity customerEntity = customerDao.selectById(customerId);
        if (customerEntity == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        customerEntity.setStatus(CustomerStatusEnum.COMMON.getCustomerStatusName());
        customerDao.updateById(customerEntity);
    }


    @Override
    public List<CustomerContractEntity> getContracts(Long customerId) {
        return customerContractDao.selectCustomerContracts(customerId);
    }


    public void updateCustomerVipDate(CustomerEntity customerEntity) throws ParseException {
        if (customerEntity == null) {
            return;
        }
        //查询顾客的所有权益
        List<CustomerContractEntity> contractList = customerContractDao.selectList(new QueryWrapper<CustomerContractEntity>().eq("customer_id", customerEntity.getCustomerId()));
        if (CollectionUtils.isEmpty(contractList)) {
            //从未消费
            customerEntity.setIsVip(0);
        } else {
            //消费过
            customerEntity.setIsVip(1);
            if (contractList.size() == 1) {
                //第一次消费
                customerEntity.setCustomerType(CustomerTypeEnum.FIRST_CONSUME.getCustomerTypeName());
            } else {
                //多次消费
                customerEntity.setCustomerType(CustomerTypeEnum.MORE_CONSUME.getCustomerTypeName());
            }
            //只有阶段的会员卡
            List<CustomerContractEntity> customerContractsOnlyDurationCard = new ArrayList<>();
            List<Date> startDates = new ArrayList<>();
            List<Date> endDates = new ArrayList<>();
            for (CustomerContractEntity contract : contractList) {
                if (contract.getContractType().equals(ContractTypeEnum.VIP_CARD_CONTRACT.getContractTypeName())) {
                    //只有非预收费的卡才有开始日期-结束日期
                    if (!contract.getContractStatus().equals(ContractStatusEnum.PREPAYMENT.getContractStatusName())) {
                        //筛选阶段卡
                        if (VipCardBigTypeEnum.DURATION_CARD.getCardTypeName().equals(contract.getVipCardBigType())) {
                            if (contract.getEndTime().after(new Date())) {
                                startDates.add(contract.getActiveTime());
                                endDates.add(contract.getEndTime());
                            }
                        }
                    }
                }
            }
            if (!CollectionUtils.isEmpty(startDates)) {
                //更新会员开始时间和结束时间
                customerEntity.setVipStartDate(DateUtils.showMinDate(startDates));
                customerEntity.setVipEndDate(DateUtils.showMaxDate(endDates));
            } else {
                customerEntity.setVipStartDate(null);
                customerEntity.setVipEndDate(null);
            }
        }
        customerDao.updateById(customerEntity);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        CustomerEntity customerEntity = customerDao.selectById(customerId);
        if (customerEntity == null) {
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }
        customerDao.deleteById(customerId);
        //删除这个人的所有合同
        customerContractDao.delete(new QueryWrapper<CustomerContractEntity>().eq("customer_id", customerId));
    }

    @Override
    public void updateCustomerVipDuration() throws ParseException {
        List<CustomerEntity> customerEntities = customerDao.selectList(new QueryWrapper<CustomerEntity>());
        for (CustomerEntity customerEntity : customerEntities) {
            this.updateCustomerVipDate(customerEntity);
        }
    }

}