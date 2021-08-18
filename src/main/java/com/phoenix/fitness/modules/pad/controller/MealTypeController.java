package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.service.MealTypeService;
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

import java.util.Map;

/**
 * pad端
 * 餐食类型接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("pad/mealType")
@AllArgsConstructor
@Api("餐食类型管理")
public class MealTypeController {

    private final MealTypeService mealTypeService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("餐食类型列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = mealTypeService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

}
