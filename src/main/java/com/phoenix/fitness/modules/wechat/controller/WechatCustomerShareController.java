package com.phoenix.fitness.modules.wechat.controller;

import com.phoenix.fitness.modules.fitness.dto.CustomerShareWithImagesDto;
import com.phoenix.fitness.modules.fitness.service.CustomerShareService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;

import javax.validation.Valid;
import java.util.Map;

/**
 * 小程序端
 * 顾客分享接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-01-11 10:58:06
 */
@RestController
@RequestMapping("wechat/customerShare")
@AllArgsConstructor
public class WechatCustomerShareController {

    private final CustomerShareService customerShareService;

    /**
     * 列表
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<R> oneCustomerList(@PathVariable("customerId") Long customerId,@RequestParam Map<String, Object> params){
        PageUtils page= customerShareService.oneCustomerList(params,customerId);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 保存
     */
    @PostMapping()
    public ResponseEntity<R> save(@RequestBody @Valid CustomerShareWithImagesDto customerShare){
		customerShareService.save(customerShare);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    public ResponseEntity<R> update(@RequestBody @Valid CustomerShareWithImagesDto customerShare){
		customerShareService.updateById(customerShare);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
