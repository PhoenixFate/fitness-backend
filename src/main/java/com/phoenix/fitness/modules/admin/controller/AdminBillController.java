package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.service.CustomerContractService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.dto.TotalBillDto;
import com.phoenix.fitness.modules.admin.form.BillSearchForm;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;

/**
 * 账单流水
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-06-18 11:28:53
 */
@RestController
@RequestMapping("sys/bill")
@AllArgsConstructor
@Api("账单流水")
public class AdminBillController {

    private final CustomerContractService customerContractService;

    /**
     * 列表
     */
    @GetMapping("")
    public ResponseEntity<R> list(BillSearchForm billSearchForm) throws ParseException {
        PageUtils page = customerContractService.queryBillPage(billSearchForm);
        return ResponseEntity.ok(new R(page));
    }


    /**
     * 列表
     */
    @GetMapping("/total")
    public ResponseEntity<R> total(BillSearchForm billSearchForm) throws ParseException {
        TotalBillDto totalBillDto = customerContractService.getTotal(billSearchForm);
        return ResponseEntity.ok(new R(totalBillDto));
    }

    /**
     * 导出Excel
     */
    @GetMapping("/export")
    public void exportToExcel(BillSearchForm billSearchForm, HttpServletResponse response) throws ParseException {
        customerContractService.exportToExcel(billSearchForm, response);
    }


}
