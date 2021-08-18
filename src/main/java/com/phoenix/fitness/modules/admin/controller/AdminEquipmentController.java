package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.entity.EquipmentEntity;
import com.phoenix.fitness.modules.fitness.service.EquipmentService;
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
 * 设备接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/equipment")
@AllArgsConstructor
@Api("设备管理")
public class AdminEquipmentController {

    private final EquipmentService equipmentService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("设备列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = equipmentService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("设备详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
        EquipmentEntity equipment = equipmentService.getById(id);
        return ResponseEntity.ok(new R(equipment));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation("新增设备")
    public ResponseEntity<R> save(@RequestBody @Valid EquipmentEntity equipment){
        equipmentService.save(equipment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("更新设备")
    public ResponseEntity<R> update(@RequestBody @Valid EquipmentEntity equipment){
        equipmentService.updateById(equipment);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除设备")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
        equipmentService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
