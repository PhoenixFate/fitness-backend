package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.dto.AdminHomeToday;
import com.phoenix.fitness.modules.fitness.dto.CustomerCountDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerMoneyDto;
import com.phoenix.fitness.modules.fitness.service.StatisticsService;
import com.phoenix.fitness.modules.fitness.vo.PeriodVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;

import java.util.List;

/**
 * admin端
 * 统计数据接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@RestController
@RequestMapping("sys/statistics")
@Api("统计数据")
@AllArgsConstructor
public class AdminStatisticsController {

    private final StatisticsService statisticsService;

    /**
     * admin首页今日数据
     */
    @GetMapping("/today")
    @ApiOperation("admin首页今日数据")
    public ResponseEntity<R> todayData(){
        AdminHomeToday adminToday = statisticsService.getAdminToday();
        return ResponseEntity.ok(new R(adminToday));
    }

    /**
     * admin首页某时间段添加客户数据
     */
    @PostMapping("/customer/count")
    @ApiOperation("admin首页某时间段添加客户数据")
    public ResponseEntity<R> customerCount(@RequestBody PeriodVO periodVO){
        List<CustomerCountDto> customerCountDtoList= statisticsService.getCustomerCountPeriod(periodVO);
        return ResponseEntity.ok(new R(customerCountDtoList));
    }

    /**
     * admin首页某时间段添加Vip客户数据
     */
    @PostMapping("/vip/count")
    @ApiOperation("admin首页某时间段添加Vip客户数据")
    public ResponseEntity<R> vipCustomerCount(@RequestBody PeriodVO periodVO){
        List<CustomerCountDto> customerCountDtoList= statisticsService.getVipCustomerCountPeriod(periodVO);
        return ResponseEntity.ok(new R(customerCountDtoList));
    }

    /**
     * admin首页某时间段销售金额
     */
    @PostMapping("/money")
    @ApiOperation("admin首页某时间段充值金额")
    public ResponseEntity<R> customerMoney(@RequestBody PeriodVO periodVO){
        List<CustomerMoneyDto> customerMoneyDtoList= statisticsService.getCustomerMoneyPeriod(periodVO);
        return ResponseEntity.ok(new R(customerMoneyDtoList));
    }

}
