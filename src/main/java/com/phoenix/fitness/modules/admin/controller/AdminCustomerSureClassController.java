package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.entity.CustomerSureClassLog;
import com.phoenix.fitness.modules.fitness.service.CustomerSureClassService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.form.CustomerSureClassSearchForm;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 销课记录
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-05-11 16:23:55
 */
@RestController
@RequestMapping("sys/customerSureClass")
@AllArgsConstructor
public class AdminCustomerSureClassController {

    private final CustomerSureClassService customerSureClassService;

    /**
     * 列表
     */
    @GetMapping("")
    public ResponseEntity<R> list(CustomerSureClassSearchForm customerSureClassSearchForm) throws ParseException {
        PageUtils page = customerSureClassService.queryPage(customerSureClassSearchForm);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 某个合同所有的销课记录
     */
    @GetMapping("contract/{contractId}")
    @ApiOperation("打卡列表")
    public ResponseEntity<R> customerSureClassList(@PathVariable("contractId") Long contractId) {
        List<CustomerSureClassLog> customerSureClassLogList = customerSureClassService.getCustomerSureClassList(contractId);
        return ResponseEntity.ok(new R(customerSureClassLogList));
    }

    /**
     * 修改打卡记录
     */
    @PutMapping("")
    @ApiOperation("修改打卡记录")
    public ResponseEntity<R> update(@RequestBody CustomerSureClassLog customerSureClassLog) {
        customerSureClassService.updateById(customerSureClassLog);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除销课记录
     */
    @DeleteMapping("/{customerSureClassId}")
    @ApiOperation("删除销课记录")
    public ResponseEntity<R> delete(@PathVariable("customerSureClassId") Long customerSureClassId) {
        customerSureClassService.deleteSureClass(customerSureClassId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 导出销课记录
     */
    @GetMapping("/export")
    public void exportToExcel(CustomerSureClassSearchForm customerSureClassSearchForm, HttpServletResponse response) throws ParseException {
        customerSureClassService.export(customerSureClassSearchForm, response);
    }

}
