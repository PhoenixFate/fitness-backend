package com.phoenix.fitness.modules.wechat.controller;

import com.phoenix.fitness.modules.fitness.entity.BodyTestDataEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerService;
import com.phoenix.fitness.modules.fitness.vo.CoachChangeForm;
import com.phoenix.fitness.modules.fitness.vo.CustomerPeriodVO;
import com.phoenix.fitness.modules.fitness.vo.WeChatLoginForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;

import javax.validation.Valid;
import java.util.List;

/**
 * 小程序端
 * 顾客接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("wechat/customer")
@AllArgsConstructor
@Api("顾客管理")
public class WechatCustomerController {

    private final CustomerService customerService;

    /**
     * 小程序端顾客登录
     * @param weChatLoginForm 登录表单
     * @return
     */
    @PostMapping("/login")
    private ResponseEntity<R> login(@RequestBody @Valid WeChatLoginForm weChatLoginForm){
        CustomerEntity customer = customerService.weChatLogin(weChatLoginForm);
        return ResponseEntity.ok(new R(customer));
    }

    /**
     * 顾客详情
     */
    @GetMapping("/{id}")
    @ApiOperation("顾客详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
		CustomerEntity customer = customerService.getDetail(id);
        return ResponseEntity.ok(new R(customer));
    }

    /**
     * 根据客户编号获得客户信息
     */
    @GetMapping("/number/{customerNumber}")
    @ApiOperation("根据客户编号获得客户信息")
    public ResponseEntity<R> infoByNumber(@PathVariable("customerNumber") Long customerNumber){
        CustomerEntity customer = customerService.getInfoByNumber(customerNumber);
        return ResponseEntity.ok(new R(customer));
    }

    /**
     * 根据手机号获得客户信息
     */
    @GetMapping("/mobile/{mobile}")
    @ApiOperation("根据手机号获得客户信息")
    public ResponseEntity<R> infoByMobile(@PathVariable("mobile") String mobile){
        CustomerEntity customer = customerService.getInfoByMobile(mobile);
        return ResponseEntity.ok(new R(customer));
    }


    /**
     * 顾客一段时间内的所有的体测数据
     */
    @PostMapping("/bodyTest")
    private ResponseEntity<R> login(@RequestBody CustomerPeriodVO periodVO){
        List<BodyTestDataEntity> bodyTestDataEntityList = customerService.bodyTestList(periodVO);
        return ResponseEntity.ok(new R(bodyTestDataEntityList));
    }


    /**
     * 顾客更换教练
     */
    @PutMapping("/coach/change")
    private ResponseEntity<R> login(@RequestBody @Valid CoachChangeForm coachChangeForm){
        customerService.changeCoach(coachChangeForm);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
