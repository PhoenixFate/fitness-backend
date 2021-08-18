package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.BodyStatusDataEntity;
import com.phoenix.fitness.modules.fitness.entity.BodyTestDataEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerDietPlanEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.annotation.Login;
import com.phoenix.fitness.modules.pad.annotation.LoginUser;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * pad端
 * 顾客接口
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("pad/customer")
@AllArgsConstructor
@Api("顾客管理")
@Slf4j
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 顾客列表
     */
    @Login
    @GetMapping()
    @ApiOperation("顾客列表")
    public ResponseEntity<R> list(CustomerSearchForm customerSearchForm, @LoginUser UserEntity user) {
        PageUtils page = customerService.queryPage(customerSearchForm, null);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 非vip客户列表
     */
    @Login
    @GetMapping("/notVip")
    @ApiOperation("客户非VIP列表")
    public ResponseEntity<R> listNotVip(@RequestParam Map<String, Object> params, @LoginUser UserEntity user) {
        PageUtils page = customerService.notVipList(params, null);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 顾客详情
     */
    @GetMapping("/{customerId}")
    @ApiOperation("顾客详情")
    public ResponseEntity<R> detail(@PathVariable("customerId") Long customerId) {
        CustomerEntity customer = customerService.getDetail(customerId);
        return ResponseEntity.ok(new R(customer));
    }

    /**
     * 顾客-基础信息
     */
    @GetMapping("base/{customerId}")
    @ApiOperation("顾客基础信息")
    public ResponseEntity<R> info(@PathVariable("customerId") Long customerId) {
        CustomerEntity customer = customerService.getById(customerId);
        return ResponseEntity.ok(new R(customer));
    }

    /**
     * 顾客所有的体测数据
     */
    @GetMapping("/bodyTest/{customerId}")
    @ApiOperation("顾客所有的体测数据")
    public ResponseEntity<R> bodyTestList(@PathVariable("customerId") Long customerId) {
        List<BodyTestDataEntity> bodyTestList = customerService.getBodyTestList(customerId);
        return ResponseEntity.ok(new R(bodyTestList));
    }

    /**
     * 顾客新增体测数据
     */
    @PostMapping("/bodyTest")
    @ApiOperation("顾客新增体测数据")
    public ResponseEntity<R> addBodyTest(@RequestBody @Valid BodyTestDataEntity bodyTest) {
        customerService.addBodyTest(bodyTest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 顾客修改体测数据
     */
    @PutMapping("/bodyTest")
    @ApiOperation("顾客修改体测数据")
    public ResponseEntity<R> updateBodyTest(@RequestBody BodyTestDataEntity bodyTest) {
        customerService.updateBodyTest(bodyTest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 顾客所有的体态数据
     * in favor of using auto connection
     */
    @GetMapping("/bodyStatus/{customerId}")
    @ApiOperation("顾客所有的体态数据")
    @Deprecated
    public ResponseEntity<R> bodyStatusList(@PathVariable("customerId") Long customerId) {
        List<BodyStatusDataEntity> bodyStatusList = customerService.getBodyStatusList(customerId);
        return ResponseEntity.ok(new R(bodyStatusList));
    }

    /**
     * 顾客新增体态数据
     * in favor of using auto connection
     */
    @PostMapping("/bodyStatus")
    @ApiOperation("顾客新增体态数据")
    @Deprecated
    public ResponseEntity<R> addBodyStatus(@RequestBody @Valid BodyStatusDataEntity bodyStatus) {
        customerService.addBodyStatus(bodyStatus);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 顾客修改体态数据
     * in favor of using auto connection
     */
    @PutMapping("/bodyStatus")
    @ApiOperation("顾客修改体态数据")
    @Deprecated
    public ResponseEntity<R> updateBodyStatus(@RequestBody BodyStatusDataEntity bodyStatus) {
        customerService.updateBodyStatus(bodyStatus);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 顾客所有饮食计划
     */
    @GetMapping("/dietPlan/{customerId}")
    @ApiOperation("顾客所有的体态数据")
    public ResponseEntity<R> dietPlanList(@PathVariable("customerId") Long customerId) {
        List<CustomerDietPlanEntity> customerDietPlanEntityList = customerService.getDietPlanList(customerId);
        return ResponseEntity.ok(new R(customerDietPlanEntityList));
    }

    /**
     * 顾客新增饮食计划
     */
    @PostMapping("/dietPlan")
    @ApiOperation("顾客新增体态数据")
    public ResponseEntity<R> addDietPlan(@RequestBody @Valid CustomerDietPlanEntity customerDietPlanEntity) {
        customerService.addDietPlan(customerDietPlanEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 顾客修改饮食计划
     */
    @PutMapping("/dietPlan")
    @ApiOperation("顾客新增体态数据")
    public ResponseEntity<R> updateDietPlan(@RequestBody @Valid CustomerDietPlanEntity customerDietPlanEntity) {
        customerService.updateDietPlan(customerDietPlanEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 保存
     */
    @Login
    @PostMapping()
    @ApiOperation("新增顾客")
    public ResponseEntity<R> save(@RequestBody CustomerEntity customer, @LoginUser UserEntity user) {
        CustomerEntity customerEntity = customerService.saveCustomerSpecial(customer, null);
        return ResponseEntity.ok(new R(customerEntity));
    }

    /**
     * 修改
     */
    @Login
    @PutMapping()
    @ApiOperation("修改顾客")
    public ResponseEntity<R> update(@RequestBody CustomerEntity customer, @LoginUser UserEntity user) {
        customerService.updateById(customer, null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 修改客户基本信息
     */
    @PutMapping("/base")
    @ApiOperation("修改客户基本信息")
    public ResponseEntity<R> updateCommon(@RequestBody CustomerEntity customer) {
        customerService.updateCommon(customer);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 顾客所有的训练计划
     */
    @GetMapping("/customerPlan/{customerId}")
    @ApiOperation("顾客所有的训练计划")
    public ResponseEntity<R> customerPlanList(@PathVariable("customerId") Long customerId) {
        List<CustomerPlanWithAllDto> customerPlanList = customerService.getCustomerPlanList(customerId);
        return ResponseEntity.ok(new R(customerPlanList));
    }

    /**
     * 顾客所有的训练计划
     */
    @GetMapping("/updateCustomerVipDuration")
    @ApiOperation("顾客所有的训练计划")
    public ResponseEntity<R> updateCustomerVipDuration() throws ParseException {
        customerService.updateCustomerVipDuration();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
