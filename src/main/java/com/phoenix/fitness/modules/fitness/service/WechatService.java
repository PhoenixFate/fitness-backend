package com.phoenix.fitness.modules.fitness.service;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.phoenix.fitness.modules.fitness.dto.PreOderResultDto;
import com.phoenix.fitness.modules.pad.form.OrderForm;

/**
 * @author phoenix
 * @date 2020/8/19 13:42
 */
public interface WechatService {
    /**
     * 微信支付-预下单
     * @return
     * @throws Exception
     */
    PreOderResultDto newPreOrder(OrderForm orderForm) throws Exception;

    /**
     * 微信回调接口
     */
    void notify(JSONObject jsonObject);
    /**
     * 查询支付宝订单
     * @param orderId
     * @return
     * @throws Exception
     */
    AlipayTradeQueryResponse queryOrder(String orderId) throws Exception;
    /**
     *  微信支付
     *  获取平台证书列表
     * @return
     */
    String getPlatCertList() throws Exception;

    Object newPreOrderMni(OrderForm orderForm) throws Exception;
}
