package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.dto.DietPlanWithItemsDto;
import com.phoenix.fitness.modules.fitness.service.DietPlanService;
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
import java.util.Map;

/**
 * pad端
 * 饮食计划接口
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("pad/dietPlan")
@AllArgsConstructor
@Api("饮食计划管理")
public class DietPlanController {

    private final DietPlanService dietPlanService;

    /**
     * 列表
     */
    @Login
    @GetMapping()
    @ApiOperation("饮食计划列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params, @LoginUser UserEntity user) {
        PageUtils page = dietPlanService.queryPage(params, null);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("饮食计划详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id) {
        DietPlanWithItemsDto dietPlan = dietPlanService.getDetail(id);
        return ResponseEntity.ok(new R(dietPlan));
    }

    /**
     * 保存
     */
    @Login
    @PostMapping()
    @ApiOperation("新增饮食计划")
    public ResponseEntity<R> save(@RequestBody @Valid DietPlanWithItemsDto dietPlan, @LoginUser UserEntity user) {
        dietPlan.setGymId(user.getGymId());
        dietPlan.setPartnerId(user.getPartnerId());
        dietPlanService.save(dietPlan);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改饮食计划")
    public ResponseEntity<R> update(@RequestBody @Valid DietPlanWithItemsDto dietPlan) {
        dietPlanService.updateById(dietPlan);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除饮食计划")
    public ResponseEntity<R> delete(@PathVariable("id") Long id) {
        dietPlanService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
