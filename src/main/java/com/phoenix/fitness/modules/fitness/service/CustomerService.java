package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;
import com.phoenix.fitness.modules.fitness.dto.CustomerInPhysicalCardDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.vo.CoachChangeForm;
import com.phoenix.fitness.modules.fitness.vo.CustomerPeriodVO;
import com.phoenix.fitness.modules.fitness.vo.WeChatLoginForm;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 顾客service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
public interface CustomerService extends IService<CustomerEntity> {

  PageUtils queryPage(CustomerSearchForm customerSearchForm, UserEntity user);

  PageUtils notVipList(Map<String, Object> params, UserEntity uesr);

  Boolean updateCommon(CustomerEntity customer);

  CustomerEntity getInfoByNumber(Long customerNumber);

  CustomerEntity getInfoByMobile(String mobile);

  CustomerEntity weChatLogin(WeChatLoginForm weChatLoginForm);

  CustomerEntity saveCustomerSpecial(CustomerEntity customer, UserEntity user);

  Boolean updateById(CustomerEntity customer, UserEntity user);

  List<BodyTestDataEntity> getBodyTestList(Long customerId);

  void addBodyTest(BodyTestDataEntity bodyTest);

  void updateBodyTest(BodyTestDataEntity bodyTest);

  /**
   * deprecated due to in favor of automatic connect
   * using PhysiqueExamPosture instead
   *
   * @param customerId
   */
  @Deprecated
  List<BodyStatusDataEntity> getBodyStatusList(Long customerId);

  /**
   * deprecated due to in favor of automatic connect
   * using PhysiqueExamPosture instead
   *
   * @param bodyStatus
   */
  @Deprecated
  void addBodyStatus(BodyStatusDataEntity bodyStatus);

  /**
   * deprecated due to in favor of automatic connect
   * using PhysiqueExamPosture instead
   *
   * @param bodyStatus
   */
  @Deprecated
  void updateBodyStatus(BodyStatusDataEntity bodyStatus);

  List<CustomerDietPlanEntity> getDietPlanList(Long customerId);

  void addDietPlan(CustomerDietPlanEntity customerDietPlanEntity);

  void updateDietPlan(CustomerDietPlanEntity customerDietPlanEntity);

  CustomerEntity getDetail(Long customerId);

  List<CustomerPlanWithAllDto> getCustomerPlanList(Long customerId);

  List<BodyTestDataEntity> bodyTestList(CustomerPeriodVO periodVO);

  Boolean changeCoach(CoachChangeForm coachChangeForm);

  CustomerInPhysicalCardDto getInfoByPhysicalCard(String physicalCard);

  void stopCard(Long customerId);

  void cancel(Long customerId);

  void cancelStop(Long customerId);

  List<CustomerContractEntity> getContracts(Long customerId);

  void deleteCustomer(Long customerId);

  void updateCustomerVipDuration() throws ParseException;

  void updateCustomerVipDate(CustomerEntity customerEntity) throws ParseException;

}

