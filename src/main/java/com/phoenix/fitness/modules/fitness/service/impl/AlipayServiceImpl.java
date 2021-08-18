package com.phoenix.fitness.modules.fitness.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.phoenix.fitness.common.constant.AlipayConstant;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.constant.OrderStatusEnum;
import com.phoenix.fitness.common.constant.PayWayEnum;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.entity.OrderEntity;
import com.phoenix.fitness.modules.fitness.entity.PayPreOrderLogEntity;
import com.phoenix.fitness.modules.fitness.entity.PayQueryLogEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.phoenix.fitness.common.constant.*;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.exception.RRException;
import com.phoenix.fitness.common.utils.QrCodeUtil;
import com.phoenix.fitness.modules.fitness.service.OrderService;
import com.phoenix.fitness.modules.pad.form.OrderForm;
import com.phoenix.fitness.modules.fitness.dao.*;
import com.phoenix.fitness.modules.fitness.dto.PreOderResultDto;
import com.phoenix.fitness.modules.fitness.entity.*;
import com.phoenix.fitness.modules.fitness.service.AlipayService;
import com.phoenix.fitness.modules.fitness.vo.AlipayJsonRootBean;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * 支付宝servive
 *
 * @author phoenix
 * @date 2021/01/20 13:42
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlipayServiceImpl implements AlipayService {

    /**
     * 生成的二维码存放路径
     */
    @Value("${fitness.pay.qrCodePath}")
    private String qrCodePath;
    /**
     * 支付宝请求地址
     */
    @Value("${fitness.alipay.aliUrl}")
    private String aliUrl;
    /**
     * 支付宝应用ID
     */
    @Value("${fitness.alipay.aliAppId}")
    private String aliAppId;
    /**
     * 本地通过"支付宝开放平台开发助手"生成的私钥
     */
    @Value("${fitness.alipay.aliAppPrivateKey}")
    private String aliAppPrivateKey;
    /**
     * 支付宝应用设置本地公钥后生成对应的支付宝公钥（非本地生成的公钥）
     */
    @Value("${fitness.alipay.alipayPublicKey}")
    private String alipayPublicKey;
    /**
     * 支付宝回调的接口地址
     */
    @Value("${fitness.alipay.aliNotifyUrl}")
    private String aliNotifyUrl;

    private final VipCardDao vipCardDao;

    private final OrderDao orderDao;

    private final OrderService orderService;

    private final OrderItemDao orderItemDao;

    private final PayPreOrderLogDao payPreOrderLogDao;

    private final PayQueryLogDao payQueryLogDao;

    private final CustomerDao customerDao;

    private final CustomerOpenVipHistoryDao customerOpenVipHistoryDao;

    private final CustomerChargeHistoryDao customerChargeHistoryDao;

    private final CustomerVipDurationDao customerVipDurationDao;

    @Override
    public PreOderResultDto newPreOrder(OrderForm orderForm) throws Exception {
        OrderEntity order = orderService.preOrder(orderForm);
        //调用支付宝生成支付二维码
        log.info("开始调用支付宝生成支付二维码...");
        PayPreOrderLogEntity alipayPreOrderLog = new PayPreOrderLogEntity();
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, aliAppId, aliAppPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        //设置请求参数
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(order.getOrderNumber());
        model.setTotalAmount("10.00");
        model.setSubject(order.getOrderDescription());
        //如果没有店铺号可不设置
        // model.setStoreId("9527");
        model.setQrCodeTimeoutExpress("100m");//100分钟
        request.setBizModel(model);
        //支付宝异步通知地址
        request.setNotifyUrl(aliNotifyUrl);
        alipayPreOrderLog.setCoachId(order.getCoachId());
        alipayPreOrderLog.setCustomerId(order.getCustomerId());
        alipayPreOrderLog.setRequestParams(JSONObject.toJSONString(request));
        log.info("创建支付宝订单，请求参数：{} ", JSONObject.toJSONString(request));
        //调用接口
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        log.info("创建支付宝订单，返回值：{} ", JSONObject.toJSONString(response));
        alipayPreOrderLog.setResponseResult(JSONObject.toJSONString(response));
        alipayPreOrderLog.setOrderNumber(order.getOrderNumber());
        alipayPreOrderLog.setPayType(PayWayEnum.ALIPAY.getPayWayName());
        payPreOrderLogDao.insert(alipayPreOrderLog);
        if (!response.isSuccess()) {
            throw new FitnessException(ExceptionEnum.MachineOrderAlipayException);
        }
        AlipayJsonRootBean alipayJsonRootBean = JSONObject.parseObject(response.getBody(), AlipayJsonRootBean.class);
        if (!AlipayConstant.SuccessCode.equals(alipayJsonRootBean.getAlipay_trade_precreate_response().getCode())) {
            throw new RRException(ExceptionEnum.MachineOrderAlipayException);
        }
        //成功
        //修改订单状态
        order.setOrderStatus(OrderStatusEnum.UNPAID.getStatusName());
        orderDao.updateById(order);

        //将返回的二维码存储至本地
        String path = qrCodePath + order.getOrderNumber() + "-AliPay.jpg";
        String httpPath = "pad/order/qrCode/" + order.getOrderNumber() + "-AliPay.jpg";
        File file = new File(path);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        QrCodeUtil.createQrCode(new FileOutputStream(file), response.getQrCode());

        PreOderResultDto preOderResultDto = new PreOderResultDto();
        preOderResultDto.setOrderNumber(order.getOrderNumber());
        preOderResultDto.setQrCodeImageUrl(httpPath);
        return preOderResultDto;
    }

    @Override
    public void aliNotify(Map<String, String> param) throws Exception {
        log.info("支付宝异步回调接口数据处理");
        //只有支付成功后，支付宝才会回调应用接口，可直接获取支付宝响应的参数
        String orderNumber = param.get(AlipayConstant.AliOutTradeNo);
        //出于安全考虑，通过支付宝回传的订单号查询支付宝交易信息
        AlipayTradeQueryResponse aliResp = queryOrder(orderNumber);
        if (!AlipayConstant.SuccessCode.equals(aliResp.getCode())) {
            //返回值非10000
            throw new FitnessException(ExceptionEnum.MachineOrderAlipayException);
        }
        if (!AlipayConstant.AliTradeSuccess.equals(aliResp.getTradeStatus()) && !AlipayConstant.AliTradeFinished.equals(aliResp.getTradeStatus())) {
            //支付宝订单状态不是支付成功
            throw new FitnessException(ExceptionEnum.MachineOrderAliUnPay);
        }
        //可对支付宝响应参数AlipayTradeQueryResponse进行处理
        OrderEntity order = orderDao.selectOne(new QueryWrapper<OrderEntity>().eq("order_number", orderNumber));
        order.setPayMoney(aliResp.getBuyerPayAmount());
        order.setPayWay(PayWayEnum.ALIPAY.getPayWayName());
        //防止重复通知
        if (order.getOrderStatus().equals(OrderStatusEnum.UNPAID.getStatusName())) {
            this.orderService.afterNotify(order);
        }
    }

    @Override
    public AlipayTradeQueryResponse queryOrder(String orderNumber) throws Exception {
        log.info("查询支付宝订单，订单编号为：{}", orderNumber);
        PayQueryLogEntity alipayQueryLog = new PayQueryLogEntity();
        alipayQueryLog.setOrderNumber(orderNumber);
        alipayQueryLog.setQueryTime(new Date());

        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, aliAppId, aliAppPrivateKey, "json", "utf-8", alipayPublicKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderNumber);
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        log.info("查询支付宝订单，返回数据：{}", JSONObject.toJSONString(response));
        alipayQueryLog.setQueryResult(JSONObject.toJSONString(response));
        alipayQueryLog.setPayType(PayWayEnum.ALIPAY.getPayWayName());
        payQueryLogDao.insert(alipayQueryLog);
        return response;
    }

}
