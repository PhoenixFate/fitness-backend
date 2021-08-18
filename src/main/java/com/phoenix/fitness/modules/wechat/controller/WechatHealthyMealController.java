package com.phoenix.fitness.modules.wechat.controller;

import com.phoenix.fitness.modules.fitness.service.HealthyMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.Map;

/**
 * 小程序端
 * 健康餐接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("wechat/healthyMeal")
@AllArgsConstructor
@Api("健康餐管理")
public class WechatHealthyMealController {

    private final HealthyMealService healthyMealService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("健康餐列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = healthyMealService.queryPage(params,new UserEntity());
        return ResponseEntity.ok(new R(page));
    }

}
