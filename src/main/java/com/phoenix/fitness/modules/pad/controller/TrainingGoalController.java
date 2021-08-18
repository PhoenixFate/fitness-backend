package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.entity.TrainingGoalEntity;
import com.phoenix.fitness.modules.fitness.service.TrainingGoalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;

import javax.validation.Valid;
import java.util.Map;

/**
 * pad端
 * 训练目标接口
 * @author phoenix
 * @email @outlook.com
 * @date 2020-10-24 11:33:56
 */
@RestController
@RequestMapping("pad/trainingGoal")
@AllArgsConstructor
@Api("训练目标管理")
public class TrainingGoalController {

    private final TrainingGoalService trainingGoalService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("训练目标列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = trainingGoalService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("训练目标详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
		TrainingGoalEntity trainingGoal = trainingGoalService.getById(id);
        return ResponseEntity.ok(new R(trainingGoal));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation("新增训练目标")
    public ResponseEntity<R> save(@RequestBody @Valid TrainingGoalEntity trainingGoal){
		trainingGoalService.save(trainingGoal);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改训练目标")
    public ResponseEntity<R> update(@RequestBody @Valid TrainingGoalEntity trainingGoal){
		trainingGoalService.updateById(trainingGoal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除训练目标")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
		trainingGoalService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
