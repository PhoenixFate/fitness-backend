package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.dto.OrderWithItemsDto;
import com.phoenix.fitness.modules.fitness.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;

import java.util.Map;

/**
 * admin端
 * 订单接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/order")
@AllArgsConstructor
@Api("订单管理")
public class AdminOrderController {

    private final OrderService orderService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("订单列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("订单详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
        OrderWithItemsDto order = orderService.getDetail(id);
        return ResponseEntity.ok(new R(order));
    }

}
