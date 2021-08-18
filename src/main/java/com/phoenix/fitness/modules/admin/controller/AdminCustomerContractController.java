package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerContractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.form.ContractSearchForm;
import com.phoenix.fitness.modules.admin.form.FinishTimesForm;
import com.phoenix.fitness.modules.admin.form.RefundContractForm;

import java.text.ParseException;
import java.util.List;

/**
 * 合同管理
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-06-18 11:28:53
 */
@RestController
@RequestMapping("sys/contract")
@AllArgsConstructor
@Api("顾客管理")
public class AdminCustomerContractController {

    private final CustomerContractService customerContractService;

    /**
     * 列表
     */
    @GetMapping("")
    public ResponseEntity<R> list(ContractSearchForm contractSearchForm) {
        PageUtils page = customerContractService.queryPage(contractSearchForm);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 激活某个预收费的合同
     */
    @PutMapping("/active")
    public ResponseEntity<R> active(@RequestBody CustomerContractEntity customerContractEntity) throws ParseException {
        customerContractService.active(customerContractEntity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("更新合同")
    public ResponseEntity<R> update(@RequestBody CustomerContractEntity customerContractEntity) {
        customerContractService.updateById(customerContractEntity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * 顾客所有的合同
     */
    @GetMapping("/customer/{customerId}")
    @ApiOperation("顾客所有的权益")
    public ResponseEntity<R> getContracts(@PathVariable("customerId") Long customerId) {
        List<CustomerContractEntity> customerContractEntityList = customerContractService.getOneCustomerContracts(customerId);
        return ResponseEntity.ok(new R(customerContractEntityList));
    }

    /**
     * 保存某一条顾客的合同
     */
    @PostMapping("")
    @ApiOperation("保存某一条顾客的合同")
    public ResponseEntity<R> getContracts(@RequestBody CustomerContractEntity customerContractEntity) throws ParseException {
        customerContractService.saveContract(customerContractEntity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除某一条顾客的权益
     */
    @DeleteMapping("/{customerContractId}")
    @ApiOperation("删除某一条顾客的权益")
    public ResponseEntity<R> deleteContract(@PathVariable("customerContractId") Long customerContractId) throws ParseException {
        customerContractService.deleteContract(customerContractId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 顾客销次数
     */
    @PutMapping("/finish/times")
    @ApiOperation("顾客销次数")
    public ResponseEntity<R> finishTimes(@RequestBody FinishTimesForm finishTimesForm) {
        customerContractService.finishTimes(finishTimesForm);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 合同退款
     */
    @PutMapping("/refund")
    @ApiOperation("合同退款")
    public ResponseEntity<R> refund(@RequestBody RefundContractForm refundContractForm) {
        customerContractService.refundContract(refundContractForm);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
