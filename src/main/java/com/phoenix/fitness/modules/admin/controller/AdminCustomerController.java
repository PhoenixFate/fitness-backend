package com.phoenix.fitness.modules.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.phoenix.fitness.modules.fitness.dao.CustomerAddHistoryDao;
import com.phoenix.fitness.modules.fitness.dto.CustomerInPhysicalCardDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerVipClassCheckinDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerAddHistoryEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.service.CustomerService;
import com.phoenix.fitness.modules.fitness.service.CustomerVipClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.PeriodUtil;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;

import java.util.Date;
import java.util.List;

/**
 * admin端
 * 顾客接口
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/customer")
@AllArgsConstructor
@Api("顾客管理")
public class AdminCustomerController {

    private final CustomerService customerService;

    private final CustomerVipClassService customerVipClassService;

    private final CustomerAddHistoryDao addHistoryDao;

    /**
     * 顾客列表
     */
    @GetMapping()
    @ApiOperation("顾客列表")
    public ResponseEntity<R> list(CustomerSearchForm customerSearchForm) {
        PageUtils page = customerService.queryPage(customerSearchForm, null);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 顾客详情
     */
    @GetMapping("/{id}")
    @ApiOperation("顾客详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id) {
        CustomerEntity customer = customerService.getById(id);
        return ResponseEntity.ok(new R(customer));
    }

    /**
     * 根据客户编号获得客户信息
     */
    @GetMapping("/number/{customerNumber}")
    @ApiOperation("根据客户编号获得客户信息")
    public ResponseEntity<R> infoByNumber(@PathVariable("customerNumber") Long customerNumber) {
        CustomerEntity customer = customerService.getInfoByNumber(customerNumber);
        return ResponseEntity.ok(new R(customer));
    }

    /**
     * 根据实体卡获得客户信息
     */
    @GetMapping("/physicalCard/{physicalCard}")
    @ApiOperation("根据客户编号获得客户信息")
    public ResponseEntity<R> infoByNumber(@PathVariable("physicalCard") String physicalCard) {
        CustomerInPhysicalCardDto customer = customerService.getInfoByPhysicalCard(physicalCard);
        return ResponseEntity.ok(new R(customer));
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
     * 保存顾客信息
     */
    @PostMapping()
    @ApiOperation("保存顾客信息")
    public ResponseEntity<R> save(@RequestBody CustomerEntity customer) {
        customerService.saveCustomerSpecial(customer, null);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 顾客停卡
     */
    @PutMapping("/stop/{customerId}")
    @ApiOperation("修改客户基本信息")
    public ResponseEntity<R> stopCard(@PathVariable("customerId") Long customerId) {
        customerService.stopCard(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /**
     * 顾客取消停卡
     */
    @PutMapping("/cancelStop/{customerId}")
    @ApiOperation("修改客户基本信息")
    public ResponseEntity<R> cancelStop(@PathVariable("customerId") Long customerId) {
        customerService.cancelStop(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 顾客注销
     */
    @PutMapping("/cancel/{customerId}")
    @ApiOperation("修改客户基本信息")
    public ResponseEntity<R> cancel(@PathVariable("customerId") Long customerId) {
        customerService.cancel(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除顾客
     */
    @DeleteMapping("/{customerId}")
    @ApiOperation("删除顾客")
    public ResponseEntity<R> delete(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/vip/checking")
    @ApiOperation("获取用户私教打卡记录")
    public ResponseEntity<R> getCheckinRecords(@RequestParam(value = "customerName", required = false) String name,
                                               @RequestParam(value = "trainerId", required = false) Long trainerId,
                                               @RequestParam(value = "startAt", required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date startAt,
                                               @RequestParam(value = "endAt", required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd") Date endAt,
                                               @RequestParam(value = "page") Integer page,
                                               @RequestParam(value = "size") Integer size


    ) {
        Page<CustomerVipClassCheckinDto> result =
                customerVipClassService.getCustomerCheckinRecord(name, trainerId, startAt, endAt,
                        PageRequest.of(page, size));
        return ResponseEntity.ok(new R(result));
    }

    /**
     * 更新所有的period
     */
    @GetMapping("/updateAll")
    @ApiOperation("更新所有的period")
    public ResponseEntity<R> infoByNumber() {
        List<CustomerAddHistoryEntity> customerAddHistoryEntities = addHistoryDao.selectList(new QueryWrapper<CustomerAddHistoryEntity>());
        for (CustomerAddHistoryEntity customerAddHistoryEntity : customerAddHistoryEntities) {
            customerAddHistoryEntity.setPeriod(PeriodUtil.getPeriod(customerAddHistoryEntity.getAddTime()));
            addHistoryDao.updateById(customerAddHistoryEntity);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
