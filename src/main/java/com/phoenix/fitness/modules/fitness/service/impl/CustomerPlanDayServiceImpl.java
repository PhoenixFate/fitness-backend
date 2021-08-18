package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.dto.*;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.dto.*;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.service.*;
import com.phoenix.fitness.modules.fitness.vo.CustomerCommentVO;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("customerPlanDayService")
@AllArgsConstructor
public class CustomerPlanDayServiceImpl extends ServiceImpl<CustomerPlanDayDao, CustomerPlanDayEntity> implements CustomerPlanDayService {

  private final CustomerPlanDayDao customerPlanDayDao;

  private final ClassInfoDao classInfoDao;

  private final CustomerDao customerDao;

  private final ActionSetCustomerDao actionSetCustomerDao;

  private final ActionSetCustomerService actionSetCustomerService;

  private final ActionSetActionInCustomerDao actionSetActionInCustomerDao;

  private final ActionSetActionInCustomerService actionSetActionInCustomerService;

  private final ActionSetActionOneSetDao actionSetActionOneSetDao;

  private final ActionSetActionOneSetDetailDao actionSetActionOneSetDetailDao;

  private final ActionSetActionOneSetService actionSetActionOneSetService;

  private final ActionSetActionOneSetDetailService actionSetActionOneSetDetailService;

  private final ActionSetCustomerImageDao actionSetCustomerImageDao;

  private final ActionSetCustomerImageService actionSetCustomerImageService;


  @Override
  public List<CustomerPlanDayEntity> listOneDay(Date date, UserEntity user) {
    return customerPlanDayDao.selectCustomerPlanDayWithDetailByDate(date, user);
  }

  @Override
  public CustomerPlanDayWithAllDto getDetail(Long id) {
    CustomerPlanDayWithAllDto customerPlanDay = customerPlanDayDao.selectCustomerPlanDayWithDetail(id);
    if (customerPlanDay == null || customerPlanDay.getDeleteFlag().equals(1)) {
      throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
    }
    //如果顾客当日训练为空，则默认生成一份初始化数据
    if (CollectionUtils.isEmpty(customerPlanDay.getClassInfo().getExercises().get(0).getActionSets())) {
      //没有生成顾客某一天的训练实例
      ClassInfoWithExercisesDto classInfo = classInfoDao.selectClassInfoWithDetail(customerPlanDay.getClassId());
      for (int i = 0; i < classInfo.getExercises().size(); i++) {
        List<ActionSetInCustomerDto> customerActionSets = new ArrayList<>();
        for (ActionSetInClassDto actionSet : classInfo.getExercises().get(i).getActionSets()) {
          ActionSetInCustomerDto customerActionSet = new ActionSetInCustomerDto();
          customerActionSet.setActionSetType(actionSet.getActionSetType());
          customerActionSet.setStandardSetNumber(actionSet.getSetNumber());
          customerActionSet.setRealSetNumber(0);
          customerActionSet.setStandardReduceSetNumber(actionSet.getReduceSetNumber());
          customerActionSet.setRealReduceSetNumber(0);
          customerActionSet.setStandardRestInternal(actionSet.getRestInternal());
          customerActionSet.setRealRestInternal(0);
          customerActionSet.setSort(actionSet.getSort());
          List<ActionSetActionInCustomerEntity> actions = new ArrayList<>();
          for (ActionSetActionInClassEntity action : actionSet.getActions()) {
            ActionSetActionInCustomerEntity customerAction = new ActionSetActionInCustomerEntity();
            customerAction.setActionId(action.getActionId());
            customerAction.setActionName(action.getActionName());
            customerAction.setContainWeight(action.getContainWeight());
            customerAction.setUnit(action.getUnit());
            customerAction.setCount(action.getCount());
            customerAction.setTime(action.getTime());
            customerAction.setWeight(action.getWeight());
            actions.add(customerAction);
          }
          customerActionSet.setActions(actions);
          customerActionSets.add(customerActionSet);
          customerPlanDay.getClassInfo().getExercises().get(i).setActionSets(customerActionSets);
        }
      }
    }
    return customerPlanDay;
  }


  @Override
  public Boolean updateById(CustomerPlanDayWithAllDto customerPlanDay) {
    customerPlanDayDao.updateById(customerPlanDay);
    //重新维护动作组（顾客训练中的实例）
    actionSetCustomerDao.delete(new QueryWrapper<ActionSetCustomerEntity>().eq("customer_plan_day_id", customerPlanDay.getCustomerPlanDayId()));
    List<ActionSetCustomerEntity> actionSetCustomerList = new ArrayList<>();
    for (ExerciseWithActionSetCustomerDto exercise : customerPlanDay.getClassInfo().getExercises()) {
      for (int i = 0; i < exercise.getActionSets().size(); i++) {
        ActionSetCustomerEntity actionSetCustomerEntity = exercise.getActionSets().get(i);
        actionSetCustomerEntity.setCustomerId(customerPlanDay.getCustomerId());
        actionSetCustomerEntity.setCustomerPlanId(customerPlanDay.getCustomerPlanId());
        actionSetCustomerEntity.setCustomerPlanDayId(customerPlanDay.getCustomerPlanDayId());
        actionSetCustomerEntity.setExerciseId(exercise.getExerciseId());
        actionSetCustomerEntity.setSort(i);
        actionSetCustomerList.add(actionSetCustomerEntity);
      }
    }
    actionSetCustomerService.saveBatch(actionSetCustomerList);

    //重新维护每个动作的训练数据
    actionSetActionInCustomerDao.delete(new QueryWrapper<ActionSetActionInCustomerEntity>().eq("customer_plan_day_id", customerPlanDay.getCustomerPlanDayId()));
    actionSetCustomerImageDao.delete(new QueryWrapper<ActionSetCustomerImageEntity>().eq("customer_plan_day_id", customerPlanDay.getCustomerPlanDayId()));
    List<ActionSetActionInCustomerEntity> actionSetActionInCustomerEntityList = new ArrayList<>();
    List<ActionSetCustomerImageEntity> actionSetCustomerImageEntityList = new ArrayList<>();
    for (ExerciseWithActionSetCustomerDto exercise : customerPlanDay.getClassInfo().getExercises()) {
      for (ActionSetInCustomerDto actionSetCustomerEntity : exercise.getActionSets()) {
        for (int i = 0; i < actionSetCustomerEntity.getActions().size(); i++) {
          ActionSetActionInCustomerEntity action = actionSetCustomerEntity.getActions().get(i);
          action.setSort(i);
          action.setClassId(customerPlanDay.getClassId());
          action.setExerciseId(exercise.getExerciseId());
          action.setCustomerId(customerPlanDay.getCustomerId());
          action.setCustomerPlanDayId(customerPlanDay.getCustomerPlanDayId());
          action.setActionSetCustomerId(actionSetCustomerEntity.getActionSetCustomerId());
          actionSetActionInCustomerEntityList.add(action);
        }
        for (String imageUrl : actionSetCustomerEntity.getImageUrls()) {
          ActionSetCustomerImageEntity actionSetCustomerImageEntity = new ActionSetCustomerImageEntity();
          actionSetCustomerImageEntity.setCustomerId(customerPlanDay.getCustomerId());
          actionSetCustomerImageEntity.setActionSetCustomerId(actionSetCustomerEntity.getActionSetCustomerId());
          actionSetCustomerImageEntity.setCustomerPlanDayId(customerPlanDay.getCustomerPlanDayId());
          actionSetCustomerImageEntity.setImageUrl(imageUrl);
          actionSetCustomerImageEntityList.add(actionSetCustomerImageEntity);
        }
      }
    }
    actionSetActionInCustomerService.saveBatch(actionSetActionInCustomerEntityList);
    actionSetCustomerImageService.saveBatch(actionSetCustomerImageEntityList);


    //重新维护oneSet数据
    actionSetActionOneSetDao.delete(new QueryWrapper<ActionSetCustomerOneSetEntity>().eq("customer_plan_day_id", customerPlanDay.getCustomerPlanDayId()));
    List<ActionSetCustomerOneSetEntity> actionSetCustomerOneSetEntityList = new ArrayList<>();
    for (ExerciseWithActionSetCustomerDto exercise : customerPlanDay.getClassInfo().getExercises()) {
      for (ActionSetInCustomerDto actionSetCustomerEntity : exercise.getActionSets()) {
        if (!CollectionUtils.isEmpty(actionSetCustomerEntity.getOneSets())) {
          for (ActionSetCustomerOneSetEntity oneSet : actionSetCustomerEntity.getOneSets()) {
            oneSet.setCustomerPlanDayId(customerPlanDay.getCustomerPlanDayId());
            oneSet.setActionSetCustomerId(actionSetCustomerEntity.getActionSetCustomerId());
            oneSet.setSetIndex(actionSetCustomerEntity.getOneSets().indexOf(oneSet) + 1);
            actionSetCustomerOneSetEntityList.add(oneSet);
          }
        }
      }
    }
    actionSetActionOneSetService.saveBatch(actionSetCustomerOneSetEntityList);

    //重新维护oneSetDetail数据
    actionSetActionOneSetDetailDao.delete(new QueryWrapper<ActionSetCustomerOneSetDetailEntity>().eq("customer_plan_day_id", customerPlanDay.getCustomerPlanDayId()));
    List<ActionSetCustomerOneSetDetailEntity> actionSetCustomerOneSetDetailEntityList = new ArrayList<>();
    for (ExerciseWithActionSetCustomerDto exercise : customerPlanDay.getClassInfo().getExercises()) {
      for (ActionSetInCustomerDto actionSetCustomerEntity : exercise.getActionSets()) {
        if (!CollectionUtils.isEmpty(actionSetCustomerEntity.getOneSets())) {
          for (ActionSetCustomerOneSetEntity oneSet : actionSetCustomerEntity.getOneSets()) {
            if (!CollectionUtils.isEmpty(oneSet.getOneSetDetails())) {
              for (ActionSetCustomerOneSetDetailEntity oneSetDetail : oneSet.getOneSetDetails()) {
                oneSetDetail.setCustomerPlanDayId(customerPlanDay.getCustomerPlanDayId());
                oneSetDetail.setOneSetId(oneSet.getOneSetId());
                oneSetDetail.setDetailIndex(oneSet.getOneSetDetails().indexOf(oneSetDetail));
                actionSetCustomerOneSetDetailEntityList.add(oneSetDetail);
              }
            }
          }
        }
      }
    }
    actionSetActionOneSetDetailService.saveBatch(actionSetCustomerOneSetDetailEntityList);
    return true;
  }


  @Override
  public void complete(CustomerPlanDayWithAllDto customerPlanDay) {
    //当天的状态改成已完成
    customerPlanDay.setStatus(1);
    customerPlanDayDao.updateById(customerPlanDay);
    if (customerPlanDay.getCustomerId() != null) {
      CustomerEntity customerEntity = customerDao.selectById(customerPlanDay.getCustomerId());
      customerEntity.setCurrentClass(customerEntity.getCurrentClass() + 1);
      customerDao.updateById(customerEntity);
    }
    this.updateById(customerPlanDay);
  }

  @Override
  public List<CustomerPlanDayEntity> listOneCustomerPlan(Long customerPlanId) {
    return customerPlanDayDao.selectOneCustomerPlan(customerPlanId);
  }

  @Override
  public List<CustomerPlanDayEntity> listOneCustomerHistory(Long customerPlanId) {
    return customerPlanDayDao.selectOneCustomerHistory(customerPlanId);
  }

  @Override
  public List<MonthClassCountDto> listOneMonthCount(Long year, Long month, UserEntity user) {
    String yearMonth = "";
    if (month >= 0L && month < 10L) {
      yearMonth = year + "0" + month;
    } else {
      yearMonth = year + "" + month;
    }
    //当月数据
    List<MonthClassCountDto> monthClassCount1 = customerPlanDayDao.listOneMonthCount(yearMonth, user);

    //pad首页如果只显示当月数据，则pad首页日历最上面会有显示不全的问题
    //查询当前月 上个月的数据
    if (month == 1L) {
      yearMonth = (year - 1) + "12";
    } else {
      if ((month - 1) > 0L && (month - 1) < 10L) {
        yearMonth = year + "0" + (month - 1);
      } else {
        yearMonth = year + "" + (month - 1);
      }
    }
    List<MonthClassCountDto> monthClassCount2 = customerPlanDayDao.listOneMonthCount(yearMonth, user);

    //查询当前月 下个月的数据
    if (month == 12L) {
      yearMonth = (year + 1) + "01";
    } else {
      if ((month + 1) > 0L && (month + 1) < 10L) {
        yearMonth = year + "0" + (month + 1);
      } else {
        yearMonth = year + "" + (month + 1);
      }
    }
    List<MonthClassCountDto> monthClassCount3 = customerPlanDayDao.listOneMonthCount(yearMonth, user);
    monthClassCount1.addAll(monthClassCount2);
    monthClassCount1.addAll(monthClassCount3);
    return monthClassCount1;
  }

  @Override
  public List<CustomerPlanDayWithClassInfoDto> customerAllDays(Long customerId) {
    return customerPlanDayDao.selectCustomerAllDays(customerId);
  }

  @Override
  public void customerSure(CustomerCommentVO customerComment) {
    CustomerPlanDayEntity customerPlanDay = customerPlanDayDao.selectOne(new QueryWrapper<CustomerPlanDayEntity>().eq("detail_date", customerComment.getDetailDate()).eq("customer_id", customerComment.getCustomerId()));
    if (customerPlanDay == null) {
      throw new FitnessException(ExceptionEnum.CUSTOMER_PLAN_DAY_NOT_FOUND);
    }
    if (!customerPlanDay.getStatus().equals(1)) {
      //顾客只能确认教练已经完成的训练
      throw new FitnessException(ExceptionEnum.CUSTOMER_SURE_STATUS_ERROR);
    }
    customerPlanDay.setCustomerIsSure(1);
    customerPlanDay.setCustomerCommentScore(customerComment.getCommentScore());
    customerPlanDay.setCustomerCommentContent(customerComment.getCommentContent());
    customerPlanDayDao.updateById(customerPlanDay);
  }

}