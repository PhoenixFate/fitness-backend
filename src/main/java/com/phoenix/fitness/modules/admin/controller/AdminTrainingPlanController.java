package com.phoenix.fitness.modules.admin.controller;

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
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * admin端
 * 训练计划接口
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@RestController
@RequestMapping("sys/trainingPlan")
@AllArgsConstructor
@Api("训练计划管理")
public class AdminTrainingPlanController {

    private final TrainingPlanService trainingPlanService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("训练计划列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params) {
        PageUtils page = trainingPlanService.queryPage(params, new UserEntity());
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 带详情的列表
     */
    @GetMapping("/detail")
    @ApiOperation("训练计划列表详情")
    public ResponseEntity<R> listWithDetail() {
        List<TrainingPlanWithPeriodsDto> trainingPlans = trainingPlanService.selectTrainingPlanListWithDetail(new UserEntity());
        return ResponseEntity.ok(new R(trainingPlans));
    }

    /**
     * 详情
     */
    @RequestMapping("/{id}")
    @ApiOperation("训练计划详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id) {
        TrainingPlanWithPeriodsDto trainingPlan = trainingPlanService.getDetail(id);
        return ResponseEntity.ok(new R(trainingPlan));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation("新增训练计划")
    public ResponseEntity<R> save(@RequestBody @Valid TrainingPlanWithPeriodsDto trainingPlan) {
        trainingPlanService.save(trainingPlan);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改训练计划")
    public ResponseEntity<R> update(@RequestBody @Valid TrainingPlanWithPeriodsDto trainingPlan) {
        trainingPlanService.updateById(trainingPlan);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除训练计划")
    public ResponseEntity<R> delete(@PathVariable("id") Long id) {
        trainingPlanService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
