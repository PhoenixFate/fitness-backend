package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.dto.GymWithCoachesDto;
import com.phoenix.fitness.modules.fitness.service.GymService;
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
 * 健身房接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/gym")
@AllArgsConstructor
@Api("健身房管理")
public class AdminGymController {

    private final GymService gymService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("健身房列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = gymService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("健身房详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
        GymWithCoachesDto gym = gymService.getDetail(id);
        return ResponseEntity.ok(new R(gym));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation("新增健身房")
    public ResponseEntity<R> save(@RequestBody @Valid GymWithCoachesDto gym){
		gymService.save(gym);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改健身房")
    public ResponseEntity<R> update(@RequestBody @Valid GymWithCoachesDto gym){
		gymService.updateById(gym);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除健身房")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
		gymService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
