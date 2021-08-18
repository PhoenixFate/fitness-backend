package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.entity.ActionTypeEntity;
import com.phoenix.fitness.modules.fitness.service.ActionTypeService;
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
 * admin端
 * 动作分类接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@RestController
@RequestMapping("sys/actionType")
@AllArgsConstructor
@Api("动作类型管理")
public class AdminActionTypeController {

    private final ActionTypeService actionTypeService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("动作分类列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = actionTypeService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("动作分类详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
        ActionTypeEntity actionType = actionTypeService.getById(id);
        return ResponseEntity.ok(new R(actionType));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation("新增动作分类")
    public ResponseEntity<R> save(@RequestBody @Valid ActionTypeEntity actionType){
        actionTypeService.save(actionType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改动作分类")
    public ResponseEntity<R> update(@RequestBody @Valid ActionTypeEntity actionType){
        actionTypeService.updateById(actionType);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除动作分类")
    public ResponseEntity<R> delete(@PathVariable("id")Long id){
        actionTypeService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
