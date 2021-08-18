package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.service.CustomerMarketingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.dto.PeriodDto;
import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;

import java.util.List;

/**
 * admin端
 * 顾客营销接口
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/marketing")
@AllArgsConstructor
@Api("顾客营销")
public class AdminCustomerMarketingController {

    private final CustomerMarketingService customerMarketingService;

    /**
     * 顾客营销列表-会员卡剩余
     */
    @GetMapping("/card")
    @ApiOperation("顾客营销列表-会员卡剩余")
    public ResponseEntity<R> cardList(CustomerSearchForm customerSearchForm) {
        PageUtils page = customerMarketingService.queryCardPage(customerSearchForm);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 顾客营销列表-私教剩余
     */
    @GetMapping("/training")
    @ApiOperation("顾客营销列表-私教剩余")
    public ResponseEntity<R> trainingList(CustomerSearchForm customerSearchForm) {
        PageUtils page = customerMarketingService.queryTrainingPage(customerSearchForm);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 顾客添加次数
     * 访客添加次数
     */
    @GetMapping("/period/count")
    @ApiOperation("顾客/访客添加次数")
    public ResponseEntity<R> periodCount() {
        List<PeriodDto> periodCounts = customerMarketingService.periodCount();
        return ResponseEntity.ok(new R(periodCounts));
    }

    /**
     * 顾客添加次数-用于统计表
     * 访客添加次数-用于统计表
     */
    @GetMapping("/period/count/chart")
    @ApiOperation("顾客/访客添加次数")
    public ResponseEntity<R> periodCountChart() {
        List<List<Integer>> periodCounts = customerMarketingService.periodCountChart();
        return ResponseEntity.ok(new R(periodCounts));
    }


}
