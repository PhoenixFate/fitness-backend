package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.service.CustomerClockInService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;

import java.util.Map;

/**
 * 打卡记录
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-05-11 16:23:55
 */
@RestController
@RequestMapping("sys/customerClockIn")
@AllArgsConstructor
public class AdminCustomerClockInController {

    private final CustomerClockInService customerClockInService;

    /**
     * 列表
     */
    @GetMapping("")
    @ApiOperation("打卡列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params) {
        PageUtils page = customerClockInService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 打卡登录
     */
    @PostMapping("/in/{customerId}")
    public ResponseEntity<R> save(@PathVariable("customerId") Long customerId) {
        customerClockInService.clockIn(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 打卡退出
     */
    @PutMapping("/out/{customerId}")
    public ResponseEntity<R> update(@PathVariable("customerId") Long customerId) {
        customerClockInService.clockOut(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
