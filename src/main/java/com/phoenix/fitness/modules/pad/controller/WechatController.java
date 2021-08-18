package com.phoenix.fitness.modules.pad.controller;

import cn.hutool.http.ContentType;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import com.phoenix.fitness.modules.fitness.domain.WxPayV3Bean;
import com.phoenix.fitness.modules.fitness.service.WechatService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.form.OrderForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * pad端
 * 微信支付相关接口
 *
 * @author phoenix
 * @date 2020/8/26 16:55
 */
@Slf4j
@RestController
@RequestMapping("pad/wechat")
@AllArgsConstructor
@Api("微信支付")
public class WechatController {

    private final WechatService wechatService;

    private final WxPayV3Bean wxPayV3Bean;

    /**
     * 预下单
     * nativePay 直连商户模式
     * 生成微信二维码
     **/
    @PostMapping("preOrder")
    public R nativePay(@RequestBody @Valid OrderForm orderForm) throws Exception {
        return R.ok().put("order", wechatService.newPreOrder(orderForm));
    }


    /**
     * 查询订单
     **/
    @GetMapping("queryOrder/{orderId}")
    public R queryOrder(@PathVariable String orderId) throws Exception {
        // alipayService.queryOrder(orderId);
        return R.ok();
    }

    /**
     * 微信支付 回调通知接口
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/notification", method = {org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
    public void notify(HttpServletRequest request, HttpServletResponse response) {
        log.info("收到支付成功的通知");
        Map<String, String> map = new HashMap<>(12);
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");
            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            String result = HttpKit.readData(request);
            log.info("支付通知密文 {}", result);
            // 需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,
                    wxPayV3Bean.getApiKey(), wxPayV3Bean.getPlatformCertPath());
            log.info("支付通知明文 {}", plainText);
            JSONObject wechatResponse = JSONObject.parseObject(plainText);
            if (wechatResponse.getString("trade_state").equals("SUCCESS")) {
                //支付成功！
                response.setStatus(200);
                map.put("code", "SUCCESS");
                map.put("message", "SUCCESS");
                wechatService.notify(wechatResponse);
            } else {
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            }
            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信支付
     * 获取平台证书列表
     **/
    @GetMapping("/platCert/list")
    public String platCertList() throws Exception {
        // 获取平台证书列表
        return wechatService.getPlatCertList();
    }


}
