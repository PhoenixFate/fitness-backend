package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.entity.SalesmanEntity;
import com.phoenix.fitness.modules.fitness.service.SalesmanService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;

import java.util.Map;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-06-24 10:45:05
 */
@RestController
@RequestMapping("sys/salesman")
@AllArgsConstructor
@Api("销售人员")
public class AdminSalesmanController {

    private final SalesmanService salesmanService;

    /**
     * 列表
     */
    @GetMapping("")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params) {
        PageUtils page = salesmanService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }


    /**
     * 信息
     */
    @GetMapping("/{salesmanId}")
    public ResponseEntity<R> info(@PathVariable("salesmanId") Long salesmanId) {
        SalesmanEntity salesman = salesmanService.getById(salesmanId);
        return ResponseEntity.ok(new R(salesman));
    }

    /**
     * 保存
     */
    @PostMapping("")
    public ResponseEntity<R> save(@RequestBody SalesmanEntity salesman) {
        salesmanService.save(salesman);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping("")
    public ResponseEntity<R> update(@RequestBody SalesmanEntity salesman) {
        salesmanService.updateById(salesman);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<R> delete(@PathVariable("id") Long id) {
        salesmanService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
