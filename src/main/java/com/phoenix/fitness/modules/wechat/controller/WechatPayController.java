package com.phoenix.fitness.modules.wechat.controller;

import com.phoenix.fitness.modules.fitness.service.WechatService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.form.OrderForm;

import javax.validation.Valid;

/**
 * 小程序端
 * 微信支付相关接口
 *
 * @author phoenix
 * @date 2020/8/26 16:55
 */
@Slf4j
@RestController
@RequestMapping("wechat/pay")
@AllArgsConstructor
@Api("小程序微信支付")
public class WechatPayController {

    private final WechatService wechatService;

    /**
     * 预下单
     * jsApiPay 直连商户模式
     **/
    @PostMapping("preOrder")
    public R preOrder(@RequestBody @Valid OrderForm orderForm) throws Exception {
        return R.ok().put("data", wechatService.newPreOrderMni(orderForm));
    }

}
