package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.entity.VisitorEntity;
import com.phoenix.fitness.modules.fitness.service.VisitorService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.form.VisitorSearchForm;

import java.text.ParseException;


/**
 * 访客
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-07-20 16:03:42
 */
@RestController
@RequestMapping("sys/visitor")
@AllArgsConstructor
public class AdminVisitorController {

    private final VisitorService visitorService;

    /**
     * 列表
     */
    @GetMapping("")
    @ApiOperation("访客列表")
    public ResponseEntity<R> list(VisitorSearchForm visitorSearchForm) throws ParseException {
        PageUtils page = visitorService.queryPage(visitorSearchForm);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 信息
     */
    @GetMapping("/{visitorId}")
    @ApiOperation("访客详情")
    public ResponseEntity<R> info(@PathVariable("visitorId") Long visitorId) {
        VisitorEntity visitor = visitorService.getById(visitorId);
        return ResponseEntity.ok(new R(visitor));
    }

    /**
     * 保存
     */
    @PostMapping("")
    @ApiOperation("保存访客")
    public ResponseEntity<R> save(@RequestBody VisitorEntity visitor) {
        visitorService.save(visitor);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping("")
    public ResponseEntity<R> update(@RequestBody VisitorEntity visitor) {
        visitorService.updateById(visitor);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<R> delete(@PathVariable("id") Long id) {
        visitorService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
