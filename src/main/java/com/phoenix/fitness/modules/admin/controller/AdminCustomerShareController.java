package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.dto.CustomerShareWithImagesDto;
import com.phoenix.fitness.modules.fitness.service.CustomerShareService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;

import java.util.Map;

/**
 * admin端
 * 顾客分享接口-用于查看那个顾客哪天分析了哪些东西
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-01-11 10:58:06
 */
@RestController
@RequestMapping("sys/customerShare")
@AllArgsConstructor
public class AdminCustomerShareController {

    private final CustomerShareService customerShareService;

    /**
     * 列表
     */
    @GetMapping()
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = customerShareService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
        CustomerShareWithImagesDto customerShare = customerShareService.getDetail(id);
        return ResponseEntity.ok(new R(customerShare));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
		customerShareService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
