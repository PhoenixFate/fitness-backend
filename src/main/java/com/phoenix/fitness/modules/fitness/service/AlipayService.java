package com.phoenix.fitness.modules.fitness.service;

import com.alipay.api.response.AlipayTradeQueryResponse;
import com.phoenix.fitness.modules.pad.form.OrderForm;
import com.phoenix.fitness.modules.fitness.dto.PreOderResultDto;

import java.util.Map;

/**
 * @author phoenix
 * @date 2020/8/19 13:42
 */
public interface AlipayService {

    /**
     * 支付宝支付接口
     * @return
     * @throws Exception
     */
    PreOderResultDto newPreOrder(OrderForm orderForm) throws Exception;

    /**
     * 扫码付款后支付宝回调接口
     * @param param
     * @throws Exception
     */
    void aliNotify(Map<String, String> param) throws Exception;

    /**
     * 查询支付宝订单
     * @param orderId
     * @return
     * @throws Exception
     */
    AlipayTradeQueryResponse queryOrder(String orderId) throws Exception;


}
