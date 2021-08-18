package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.entity.CustomerReturnVisitEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerReturnVisitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;

import java.util.Date;
import java.util.List;

/**
 * admin端
 * 顾客回访
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/customerReturnVisit")
@AllArgsConstructor
@Api("顾客回访")
public class AdminCustomerReturnVisitController {

    private final CustomerReturnVisitService customerReturnVisitService;

    /**
     * 某个顾客所有的回访列表
     */
    @GetMapping("customer/{customerId}")
    @ApiOperation("顾客列表")
    public ResponseEntity<R> list(@PathVariable("customerId") Long customerId) {
        List<CustomerReturnVisitEntity> customerReturnVisitEntityList = customerReturnVisitService.getOneCustomerAllReturnVisit(customerId);
        return ResponseEntity.ok(new R(customerReturnVisitEntityList));
    }

    /**
     * 保存顾客回访记录
     */
    @PostMapping()
    @ApiOperation("保存顾客回访记录")
    public ResponseEntity<R> save(@RequestBody CustomerReturnVisitEntity customerReturnVisitEntity) {
        customerReturnVisitService.saveCustomerReturnVisit(customerReturnVisitEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 顾客回访提醒
     */
    @GetMapping("remind")
    @ApiOperation("顾客回访提醒")
    public ResponseEntity<R> remind(@DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<CustomerReturnVisitEntity> customerReturnVisitEntityList = customerReturnVisitService.remind(date);
        return ResponseEntity.ok(new R(customerReturnVisitEntityList));
    }

}
