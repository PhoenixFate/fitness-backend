package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.dto.TargetWithAllDto;
import com.phoenix.fitness.modules.fitness.service.TargetService;
import com.phoenix.fitness.modules.fitness.vo.TargetDeleteCoachVO;
import com.phoenix.fitness.modules.fitness.vo.TargetUpdateCoachVO;
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
 * 目标接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-12-14 17:07:42
 */
@RestController
@RequestMapping("sys/target")
@AllArgsConstructor
public class AdminTargetController {

    private final TargetService targetService;

    /**
     * 列表
     */
    @GetMapping ()
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params) {
        PageUtils page = targetService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<R> info(@PathVariable("id") Long id) {
        TargetWithAllDto target = targetService.getDetail(id);
        return ResponseEntity.ok(new R(target));
    }

    /**
     * 保存
     */
    @PostMapping()
    public ResponseEntity<R> save(@RequestBody @Valid TargetWithAllDto target) {
        targetService.save(target);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    public ResponseEntity<R> update(@RequestBody @Valid TargetWithAllDto target) {
        targetService.updateById(target);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<R> delete(@PathVariable("id") Long id) {
        targetService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 修改关联的教练
     */
    @PutMapping("/coach")
    public ResponseEntity<R> updateCoach(@RequestBody @Valid TargetUpdateCoachVO target) {
        targetService.updateCoachRelation(target);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除目标关联的教练
     */
    @DeleteMapping("/coach")
    public ResponseEntity<R> deleteCoach(@RequestBody @Valid TargetDeleteCoachVO target) {
        targetService.deleteCoachRelation(target);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
