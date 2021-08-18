package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.dto.ExerciseWithActionSetDto;
import com.phoenix.fitness.modules.fitness.service.ExerciseService;
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
 * 训练项接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@RestController
@RequestMapping("sys/exercise")
@AllArgsConstructor
@Api("训练项管理")
public class AdminExerciseController {

    private final ExerciseService exerciseService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("训练项列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = exerciseService.queryPage(params,new UserEntity());
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 列表-带详情
     */
    @GetMapping("/detail")
    @ApiOperation("训练项列表-带详情")
    public ResponseEntity<R> listWithDetail(){
        List<ExerciseWithActionSetDto> exercises = exerciseService.listWithDetail(new UserEntity());
        return ResponseEntity.ok(new R(exercises));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("训练项详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
        ExerciseWithActionSetDto exercise = exerciseService.getDetail(id);
        return ResponseEntity.ok(new R(exercise));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation("新增训练项")
    public ResponseEntity<R> save(@RequestBody @Valid ExerciseWithActionSetDto exercise){
        exerciseService.save(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改训练项")
    public ResponseEntity<R> update(@RequestBody @Valid ExerciseWithActionSetDto exercise){
        exerciseService.updateById(exercise);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除训练项")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
        exerciseService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
