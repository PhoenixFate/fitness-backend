package com.phoenix.fitness.modules.wechat.controller;

import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithAllDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithClassInfoDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanDayEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanDayService;
import com.phoenix.fitness.modules.fitness.vo.CustomerCommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;

import java.util.List;

/**
 * 微信小程序端
 * 客户训练指导接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("wechat/trainingAid")
@AllArgsConstructor
@Api("客户训练指导管理")
public class WechatTrainingAidController {

    private final CustomerPlanDayService customerPlanDayService;

    /**
     * 某顾客所有的训练列表
     */
    @GetMapping("/customer/{customerId}")
    @ApiOperation("某顾客所有的训练列表")
    public ResponseEntity<R> customerAllDays(@PathVariable("customerId") Long customerId){
        List<CustomerPlanDayWithClassInfoDto> customerPlanDays =customerPlanDayService.customerAllDays(customerId);
        return ResponseEntity.ok(new R(customerPlanDays));
    }

    /**
     * 某顾客某个训练计划的训练列表
     */
    @RequestMapping("/customer/plan/{customerPlanId}")
    @ApiOperation("某客户所有的训练指导")
    public ResponseEntity<R> listOneCustomer(@PathVariable("customerPlanId") Long customerPlanId){
        List<CustomerPlanDayEntity> customerPlanDays =customerPlanDayService.listOneCustomerPlan(customerPlanId);
        return ResponseEntity.ok(new R(customerPlanDays));
    }

    /**
     * 顾客确认完成某天的训练
     */
    @PutMapping("/customer/sure")
    @ApiOperation("顾客确认某天的训练")
    public ResponseEntity<R> complete(@RequestBody CustomerCommentVO customerComment){
        customerPlanDayService.customerSure(customerComment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 训练指导详情
     */
    @GetMapping("/{id}")
    @ApiOperation("训练指导详情")
    public ResponseEntity<R> infoDetail(@PathVariable("id") Long id){
        CustomerPlanDayWithAllDto customerPlanDay = customerPlanDayService.getDetail(id);
        return ResponseEntity.ok(new R(customerPlanDay));
    }
}
