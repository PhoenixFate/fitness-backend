package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithAllDto;
import com.phoenix.fitness.modules.fitness.dto.MonthClassCountDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerPlanDayEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerVipClassCheckinEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerPlanDayService;
import com.phoenix.fitness.modules.fitness.service.CustomerVipClassService;
import com.phoenix.fitness.modules.fitness.vo.DateVO;
import com.phoenix.fitness.modules.pad.annotation.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.annotation.Login;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.List;

/**
 * pad端
 * 客户训练指导接口
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("pad/customerPlanDay")
@AllArgsConstructor
@Api("客户训练指导管理")
public class CustomerPlanDayController {

  private final CustomerPlanDayService customerPlanDayService;

  private final CustomerVipClassService customerVipClassService;

  /**
   * 单天的客户训练指导列表
   */
  @Login
  @PostMapping("/oneDay")
  @ApiOperation("单天的客户训练指导列表")
  public ResponseEntity<R> listOneDay(@RequestBody DateVO dateVO, @LoginUser UserEntity user) {
    List<CustomerPlanDayEntity> customerPlanDayEntities = customerPlanDayService.listOneDay(dateVO.getDate(), null);
    return ResponseEntity.ok(new R(customerPlanDayEntities));
  }

  /**
   * 单月的客户训练指导列表
   */
  @Login
  @GetMapping("/count/{year}/{month}")
  @ApiOperation("单月的客户训练指导列表")
  public ResponseEntity<R> listOneMonthCount(@PathVariable("year") Long year, @PathVariable("month") Long month, @LoginUser UserEntity user) {
    List<MonthClassCountDto> monthClassCountDtoList = customerPlanDayService.listOneMonthCount(year, month, null);
    return ResponseEntity.ok(new R(monthClassCountDtoList));
  }

  /**
   * 某客户所有的训练指导
   */
  @GetMapping("/customerPlan/{customerPlanId}")
  @ApiOperation("某客户所有的训练指导")
  public ResponseEntity<R> listOneCustomer(@PathVariable("customerPlanId") Long customerPlanId) {
    List<CustomerPlanDayEntity> customerPlanDayEntities = customerPlanDayService.listOneCustomerPlan(customerPlanId);
    return ResponseEntity.ok(new R(customerPlanDayEntities));
  }

  /**
   * 某客户历史训练指导
   */
  @GetMapping("/customerPlan/history/{customerPlanId}")
  @ApiOperation("某客户历史训练指导")
  public ResponseEntity<R> listOneCustomerHistory(@PathVariable("customerPlanId") Long customerPlanId) {
    List<CustomerPlanDayEntity> customerPlanDayEntities = customerPlanDayService.listOneCustomerHistory(customerPlanId);
    return ResponseEntity.ok(new R(customerPlanDayEntities));
  }

  /**
   * 训练指导详情
   */
  @GetMapping("/{id}")
  @ApiOperation("训练指导详情")
  public ResponseEntity<R> infoDetail(@PathVariable("id") Long id) {
    CustomerPlanDayWithAllDto customerPlanDay = customerPlanDayService.getDetail(id);
    return ResponseEntity.ok(new R(customerPlanDay));
  }

  /**
   * 更新当日训练数据
   */
  @PutMapping("")
  @ApiOperation("更新当日训练数据")
  public ResponseEntity<R> save(@RequestBody CustomerPlanDayWithAllDto customerPlanDay) {
    customerPlanDayService.updateById(customerPlanDay);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  /**
   * 完成某天的训练
   */
  @PutMapping("/complete")
  @ApiOperation("完成某天的训练")
  public ResponseEntity<R> complete(@RequestBody CustomerPlanDayWithAllDto customerPlanDay) {
    customerPlanDayService.complete(customerPlanDay);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }


  /**
   * 用户打卡
   */
  @PostMapping("/checkin")
  @ApiOperation("用户上私教课打卡")
  public ResponseEntity<R> checkin(@RequestBody CustomerVipClassCheckinEntity entity) {
    CustomerVipClassCheckinEntity result = customerVipClassService
        .customerVipClassCheckin(entity);
    if (result != null) {
      return ResponseEntity.ok(new R(result));
    }
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
