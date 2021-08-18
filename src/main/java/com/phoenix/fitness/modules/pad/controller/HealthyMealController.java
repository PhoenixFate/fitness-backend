package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.dto.HealthyMealWithItemsDto;
import com.phoenix.fitness.modules.fitness.service.HealthyMealService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.annotation.Login;
import com.phoenix.fitness.modules.pad.annotation.LoginUser;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * pad端
 * 健康餐接口
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("pad/healthyMeal")
@AllArgsConstructor
@Api("健康餐管理")
public class HealthyMealController {

    private final HealthyMealService healthyMealService;

    /**
     * 列表
     */
    @Login
    @GetMapping()
    @ApiOperation("健康餐列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params, @LoginUser UserEntity user) {
        PageUtils page = healthyMealService.queryPage(params, null);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 带详情的列表
     */
    @Login
    @GetMapping("/detail")
    @ApiOperation("带详情的健康餐列表")
    public ResponseEntity<R> listWithDetail(@LoginUser UserEntity user) {
        List<HealthyMealWithItemsDto> healthyMealWithDetailList = healthyMealService.listWithDetail(null);
        return ResponseEntity.ok(new R(healthyMealWithDetailList));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("健康餐详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id) {
        HealthyMealWithItemsDto healthyMeal = healthyMealService.getDetail(id);
        return ResponseEntity.ok(new R(healthyMeal));
    }

    /**
     * 保存
     */
    @Login
    @PostMapping()
    @ApiOperation("新增健康餐")
    public ResponseEntity<R> save(@RequestBody @Valid HealthyMealWithItemsDto healthyMeal, @LoginUser UserEntity user) {
        healthyMeal.setGymId(user.getGymId());
        healthyMeal.setPartnerId(user.getPartnerId());
        healthyMealService.save(healthyMeal);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改健康餐")
    public ResponseEntity<R> update(@RequestBody @Valid HealthyMealWithItemsDto healthyMeal) {
        healthyMealService.updateById(healthyMeal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除健康餐")
    public ResponseEntity<R> delete(@PathVariable("id") Long id) {
        healthyMealService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
