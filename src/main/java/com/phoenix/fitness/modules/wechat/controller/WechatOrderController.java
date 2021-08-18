package com.phoenix.fitness.modules.wechat.controller;

import com.phoenix.fitness.modules.fitness.entity.OrderEntity;
import com.phoenix.fitness.modules.fitness.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.phoenix.fitness.common.utils.R;

/**
 * 微信小程序端
 * 订单接口
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("wechat/order")
@RequiredArgsConstructor
@Api("订单管理")
public class WechatOrderController {

    private final OrderService orderService;

    /**
     * 根据orderNumber获取详情
     */
    @GetMapping("/number/{orderNumber}")
    @ApiOperation("订单详情")
    public ResponseEntity<R> infoByOrderNumber(@PathVariable("orderNumber") String orderNumber) {
        OrderEntity order = orderService.getDetailByOrderNumber(orderNumber);
        return ResponseEntity.ok(new R(order));
    }

}
