package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.dto.TrainingPlanWithPeriodsDto;
import com.phoenix.fitness.modules.fitness.service.TrainingPlanService;
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
 * 训练计划
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@RestController
@RequestMapping("pad/trainingPlan")
@AllArgsConstructor
@Api("训练计划管理")
public class TrainingPlanController {

    private final TrainingPlanService trainingPlanService;

    /**
     * 列表
     */
    @Login
    @GetMapping()
    @ApiOperation("训练计划列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params,@LoginUser UserEntity user){
        PageUtils page = trainingPlanService.queryPage(params,null);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 带详情的列表
     */
    @Login
    @GetMapping("/detail")
    @ApiOperation("训练计划列表详情")
    public ResponseEntity<R> listWithDetail(@LoginUser UserEntity user){
        List<TrainingPlanWithPeriodsDto> trainingPlans = trainingPlanService.selectTrainingPlanListWithDetail(null);
        return ResponseEntity.ok(new R(trainingPlans));
    }

    /**
     * 详情
     */
    @RequestMapping("/{id}")
    @ApiOperation("训练计划详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
		TrainingPlanWithPeriodsDto trainingPlan = trainingPlanService.getDetail(id);
        return ResponseEntity.ok(new R(trainingPlan));
    }

    /**
     * 保存
     */
    @Login
    @PostMapping()
    @ApiOperation("新增训练计划")
    public ResponseEntity<R> save(@RequestBody @Valid TrainingPlanWithPeriodsDto trainingPlan,@LoginUser UserEntity user){
        trainingPlan.setGymId(user.getGymId());
        trainingPlan.setPartnerId(user.getPartnerId());
		trainingPlanService.save(trainingPlan);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改训练计划")
    public ResponseEntity<R> update(@RequestBody @Valid TrainingPlanWithPeriodsDto trainingPlan){
		trainingPlanService.updateById(trainingPlan);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除训练计划")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
		trainingPlanService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
