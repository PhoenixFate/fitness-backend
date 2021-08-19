package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.constant.*;
import com.phoenix.fitness.common.utils.OrderNumberGenerator;
import com.phoenix.fitness.common.utils.Query;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.*;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.form.OrderSearchForm;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.dto.OrderWithItemsDto;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.service.OrderItemService;
import com.phoenix.fitness.modules.fitness.service.OrderService;
import com.phoenix.fitness.modules.pad.entity.UserEntity;
import com.phoenix.fitness.modules.pad.form.OrderForm;
import com.phoenix.fitness.modules.pad.form.OrderItemForm;

import java.math.BigDecimal;
import java.util.*;

@Service("orderService")
@AllArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    private final OrderDao orderDao;

    private final CoachDao coachDao;

    private final TrainingPlanDao trainingPlanDao;

    private final VipCardDao vipCardDao;

    private final OrderItemService orderItemService;

    private final OrderItemDao orderItemDao;

    private final CustomerDao customerDao;

    private final CustomerOpenVipHistoryDao customerOpenVipHistoryDao;

    private final CustomerChargeHistoryDao customerChargeHistoryDao;

    private final CustomerVipDurationDao customerVipDurationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        OrderSearchForm orderSearchForm = new OrderSearchForm();
        if (!StringUtils.isEmpty(params.get("orderNumber"))) {
            orderSearchForm.setOrderNumber((String) params.get("orderNumber"));
        }
        if (!StringUtils.isEmpty(params.get("orderType"))) {
            orderSearchForm.setOrderType((String) params.get("orderType"));
        }
        if (!StringUtils.isEmpty(params.get("customerName"))) {
            orderSearchForm.setCustomerName((String) params.get("customerName"));
        }
        if (!StringUtils.isEmpty(params.get("coachName"))) {
            orderSearchForm.setCoachName((String) params.get("coachName"));
        }
        IPage<OrderEntity> pageParams = new Query<OrderEntity>().getPage(params);
        IPage<OrderEntity> orderPage = orderDao.selectOrderListWithDetail(pageParams, orderSearchForm, new UserEntity());
        return new PageUtils(orderPage);
    }

    @Override
    public OrderWithItemsDto getDetailByOrderNumber(String orderNumber) {
        OrderWithItemsDto order = orderDao.selectOrderDetailWithNumber(orderNumber);
        if (order == null || order.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ORDER_NOT_FOUND_WITH_NUMBER);
        }
        return order;
    }

    @Override
    public OrderWithItemsDto getDetail(Long id) {
        OrderWithItemsDto order = orderDao.selectOrderWithDetail(id);
        if (order == null || order.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return order;
    }

    @Override
    public OrderEntity preOrder(OrderForm orderForm) {
        //校验订单子项
        if (CollectionUtils.isEmpty(orderForm.getOrderItems())) {
            //子项为空
            throw new FitnessException(ExceptionEnum.ORDER_ITEM_IS_NULL);
        }
        CoachEntity coach = coachDao.selectById(orderForm.getCoachId());
        // if (coach == null || coach.getPerClassPrice() == null) {
        //     throw new FitnessException(ExceptionEnum.OrderCoachIsInvalid);
        // }
        OrderEntity order = new OrderEntity();
        order.setOrderNumber(OrderNumberGenerator.getLongOrderNumber(null));
        order.setOrderType(orderForm.getOrderType());
        order.setCoachId(orderForm.getCoachId());
        order.setCustomerId(orderForm.getCustomerId());
        order.setOrderStatus(OrderStatusEnum.INITIAL.getStatusName());
        List<OrderItemEntity> orderItems = new ArrayList<>();
        BigDecimal totalPrice = new BigDecimal("0");
        TrainingPlanEntity trainingPlanEntity = null;
        for (OrderItemForm orderFormItem : orderForm.getOrderItems()) {
            //判断子项类型
            OrderItemEntity orderItem = new OrderItemEntity();
            orderItem.setOrderItemType(orderFormItem.getOrderItemType());
            orderItem.setItemCount(orderFormItem.getItemCount());
            orderItem.setCreateTime(new Date());
            orderItem.setVipCardId(orderFormItem.getVipCardId());
            if (orderFormItem.getOrderItemType().equals(OrderItemTypeEnum.TRAINING_PLAN.getStatusName())) {
                //按照整个训练计划付费
                if (orderFormItem.getTrainingPlanId() == null) {
                    throw new FitnessException(ExceptionEnum.ORDER_ITEM_TRAINING_PLAN_IS_NULL);
                }
                trainingPlanEntity = trainingPlanDao.selectById(orderFormItem.getTrainingPlanId());
                if (trainingPlanEntity == null) {
                    throw new FitnessException(ExceptionEnum.OrderItemTrainingPlanIsInvalid);
                }
                if (trainingPlanEntity.getPrice() == null) {
                    throw new FitnessException(ExceptionEnum.OrderItemTrainingPlanPriceIsInvalid);
                }
                orderItem.setItemPrice(trainingPlanEntity.getPrice());
                orderItem.setItemTotalPrice(trainingPlanEntity.getPrice());
                orderItem.setTrainingPlanId(orderFormItem.getTrainingPlanId());
                totalPrice = totalPrice.add(new BigDecimal(trainingPlanEntity.getPrice()));
            } else if (orderFormItem.getOrderItemType().equals(OrderItemTypeEnum.COACH_CLASS.getStatusName())) {
                //按照教练单节课价格*数量付费

                orderItem.setItemPrice(coach.getPerClassPrice());
                BigDecimal tempPrice = new BigDecimal(coach.getPerClassPrice());
                BigDecimal tempTotalPrice = tempPrice.multiply(new BigDecimal(orderFormItem.getItemCount()));
                //保留2位置小数、4舍5入
                orderItem.setItemTotalPrice(tempTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                orderItem.setTrainingPlanId(orderFormItem.getTrainingPlanId());
                totalPrice = totalPrice.add(tempTotalPrice.setScale(2, BigDecimal.ROUND_HALF_UP));
            } else if (orderFormItem.getOrderItemType().equals(OrderItemTypeEnum.VIP_CARD.getStatusName())) {
                //不开私教课、只付vip卡
                if (orderFormItem.getVipCardId() == null) {
                    throw new FitnessException(ExceptionEnum.OrderItemVipCardIsNull);
                }
                VipCardEntity vipCard = vipCardDao.selectById(orderFormItem.getVipCardId());
                if (vipCard == null) {
                    throw new FitnessException(ExceptionEnum.OrderItemVipCardIsInvalid);
                }
                orderItem.setItemPrice(vipCard.getPrice());
                orderItem.setItemTotalPrice(vipCard.getPrice());
                orderItem.setVipCardId(vipCard.getVipCardId());
                totalPrice = totalPrice.add(new BigDecimal(vipCard.getPrice()));
            } else {
                //类型不对
                throw new FitnessException(ExceptionEnum.OrderItemTypeIsError);
            }
            orderItems.add(orderItem);
        }

        if (order.getOrderType().equals(OrderTypeEnum.OPEN_VIP.getStatusName())) {
            order.setOrderDescription("开通健身会员卡");
        } else if (order.getOrderType().equals(OrderTypeEnum.OPEN_CLASS_BY_TOTAL.getStatusName())) {
            order.setOrderDescription("购买\"" + coach.getCoachName() + "\"私教课程");
        } else if (order.getOrderType().equals(OrderTypeEnum.OPEN_CLASS_BY_TRAINING_PLAN.getStatusName())) {
            if (trainingPlanEntity != null) {
                order.setOrderDescription("购买\"" + trainingPlanEntity.getTrainingPlanName() + "\"套餐计划");
            }
        } else {
            order.setOrderDescription("其他充值服务");
        }

        //设置订单总价、保留两位小数
        order.setTotalPrice(totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        //保存订单信息
        orderDao.insert(order);
        for (OrderItemEntity orderItemEntity : orderItems) {
            orderItemEntity.setOrderId(order.getOrderId());
        }
        orderItemService.saveBatch(orderItems);
        return order;
    }

    @Override
    public void afterNotify(OrderEntity order) {
        order.setPayTime(new Date());
        order.setOrderStatus(OrderStatusEnum.PAID.getStatusName());
        orderDao.updateById(order);
        //给顾客开通vip
        CustomerEntity customer = customerDao.selectById(order.getCustomerId());
        if (customer.getIsVip() == 0) {
            customer.setIsVip(1);
            customer.setOpenVipDate(new Date());

            //非vip会员、记录开通日志
            List<CustomerOpenVipHistoryEntity> customerOpenVipHistoryEntityList = customerOpenVipHistoryDao.selectList(new QueryWrapper<CustomerOpenVipHistoryEntity>().eq("customer_id", order.getCustomerId()));
            if (customerOpenVipHistoryEntityList == null || customerOpenVipHistoryEntityList.size() == 0) {
                CustomerOpenVipHistoryEntity customerOpenVipHistoryEntity = new CustomerOpenVipHistoryEntity();
                customerOpenVipHistoryEntity.setCustomerId(order.getCustomerId());
                customerOpenVipHistoryEntity.setCoachId(order.getCoachId());
                customerOpenVipHistoryEntity.setOperationDate(new Date());
                customerOpenVipHistoryEntity.setOrderNumber(order.getOrderNumber());
                if (order.getOrderType().equals(OrderTypeEnum.OPEN_VIP.getStatusName())) {
                    //买了会员卡
                    customerOpenVipHistoryEntity.setOpenVipType(OpenVipTypeEnum.BUY_VIP_CARD.getStatusName());
                } else {
                    //课程送会员
                    customerOpenVipHistoryEntity.setOpenVipType(OpenVipTypeEnum.CLASS_SEND.getStatusName());
                }
                customerOpenVipHistoryDao.insert(customerOpenVipHistoryEntity);
            }
        }
        //记录充值记录
        CustomerChargeHistoryEntity customerChargeHistoryEntity = new CustomerChargeHistoryEntity();
        customerChargeHistoryEntity.setCustomerId(order.getCustomerId());
        customerChargeHistoryEntity.setCoachId(order.getCoachId());
        customerChargeHistoryEntity.setMoney(order.getTotalPrice());
        customerChargeHistoryEntity.setChargeDate(new Date());
        customerChargeHistoryEntity.setChargeType(order.getOrderType());
        customerChargeHistoryEntity.setOrderNumber(order.getOrderNumber());
        customerChargeHistoryDao.insert(customerChargeHistoryEntity);
        OrderItemEntity orderItem = orderItemDao.selectList(new QueryWrapper<OrderItemEntity>().eq("order_id", order.getOrderId())).get(0);
        VipCardEntity vipCard = vipCardDao.selectById(orderItem.getVipCardId());

        //给顾客的vip时长添加某一段时间
        List<CustomerVipDurationEntity> customerVipDurationEntityList = customerVipDurationDao.selectList(new QueryWrapper<CustomerVipDurationEntity>().eq("customer_id", order.getCustomerId()).orderByAsc("create_time"));
        Calendar cal = Calendar.getInstance();
        CustomerVipDurationEntity customerVipDurationEntity = new CustomerVipDurationEntity();
        customerVipDurationEntity.setCustomerId(order.getCustomerId());
        customerVipDurationEntity.setCoachId(order.getCoachId());
        customerVipDurationEntity.setOrderNumber(order.getOrderNumber());
        customerVipDurationEntity.setVipCardId(vipCard.getVipCardId());
        customerVipDurationEntity.setDays(vipCard.getAddVipDays());

        if (customerVipDurationEntityList != null && customerVipDurationEntityList.size() > 0) {
            //曾经充值过
            //最后一次充值
            CustomerVipDurationEntity lastDuration = customerVipDurationEntityList.get(customerVipDurationEntityList.size() - 1);
            if (lastDuration.getEndDate().before(new Date())) {
                //最后一次的充值的结束时间在今天之前，则开始日期为今天
                cal.setTime(new Date());//设置起时间
                customerVipDurationEntity.setStartDate(new Date());
                customer.setVipStartDate(new Date());
            } else {
                cal.setTime(lastDuration.getEndDate());//设置起时间
                customerVipDurationEntity.setStartDate(lastDuration.getEndDate());
                customer.setVipStartDate(customerVipDurationEntityList.get(0).getStartDate());
            }
            cal.add(Calendar.DATE, vipCard.getAddVipDays());
            customer.setVipEndDate(cal.getTime());
            customerVipDurationEntity.setEndDate(cal.getTime());
        } else {
            //曾经没有充值过
            cal.setTime(new Date());//设置起时间
            cal.add(Calendar.DATE, vipCard.getAddVipDays());
            customer.setVipStartDate(new Date());
            customer.setVipEndDate(cal.getTime());
            customerVipDurationEntity.setStartDate(new Date());
            customerVipDurationEntity.setEndDate(cal.getTime());
        }
        customerDao.updateById(customer);
        customerVipDurationDao.insert(customerVipDurationEntity);
    }

}