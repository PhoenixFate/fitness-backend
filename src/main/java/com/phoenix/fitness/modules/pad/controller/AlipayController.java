package com.phoenix.fitness.modules.pad.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.phoenix.fitness.modules.fitness.dao.PayNotifyLogDao;
import com.phoenix.fitness.modules.fitness.entity.PayNotifyLogEntity;
import com.phoenix.fitness.modules.fitness.service.AlipayService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.constant.AlipayConstant;
import com.phoenix.fitness.common.constant.PayWayEnum;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.form.OrderForm;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * pad端
 * 支付宝相关接口
 *
 * @author phoenix
 * @date 2020/8/26 16:55
 */
@Slf4j
@RestController
@RequestMapping("pad/alipay")
@RequiredArgsConstructor
@Api("支付宝支付")
public class AlipayController {

    /**
     * 支付宝应用设置本地公钥后生成对应的支付宝公钥（非本地生成的公钥）
     */
    @Value("${fitness.alipay.alipayPublicKey}")
    private String alipayPublicKey;

    private final AlipayService alipayService;

    private final PayNotifyLogDao payNotifyLogDao;

    /**
     * 预下单
     * 生成支付宝二维码
     **/
    @PostMapping("preOrder")
    public R qrCode(@RequestBody @Valid OrderForm orderForm) throws Exception {
        return R.ok().put("order", alipayService.newPreOrder(orderForm));
    }

    /**
     * 查询订单
     **/
    @GetMapping("queryOrder/{orderId}")
    public R queryOrder(@PathVariable String orderId) throws Exception {
        alipayService.queryOrder(orderId);
        return R.ok();
    }

    /**
     * 支付宝回调接口
     * 不返回success，支付宝会在25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）才会结束通知发送。
     */
    @RequestMapping(value = "aliNotify")
    public String aliNotify(HttpServletRequest request) throws Exception {
        try {
            log.info("进入支付宝回调地址");
            PayNotifyLogEntity alipayNotifyLog = new PayNotifyLogEntity();
            Map<String, String> params = new HashMap<>();
            Map<String, String[]> requestParams = request.getParameterMap();
            log.info("支付宝验签参数：{}", JSON.toJSONString(requestParams));
            alipayNotifyLog.setNotifyTime(new Date());
            alipayNotifyLog.setNotifyContent(JSON.toJSONString(requestParams));
            for (String name : requestParams.keySet()) {
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            boolean flag = AlipaySignature.rsaCheckV1(params, alipayPublicKey, "UTF-8", "RSA2");
            alipayNotifyLog.setOrderNumber(params.get(AlipayConstant.AliOutTradeNo));
            alipayNotifyLog.setPayType(PayWayEnum.ALIPAY.getPayWayName());
            payNotifyLogDao.insert(alipayNotifyLog);
            if (flag) {
                alipayService.aliNotify(params);
                log.info("支付宝通知更改状态成功！");
                return "success";
            }
        } catch (Throwable e) {
            log.error("exception: ", e);
        }
        return "failure";
    }

}
