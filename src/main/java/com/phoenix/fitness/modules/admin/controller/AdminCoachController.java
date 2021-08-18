package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.dto.CoachDetailDto;
import com.phoenix.fitness.modules.fitness.service.CoachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.form.CoachSearchForm;
import com.phoenix.fitness.modules.pad.form.RegisterForm;

import javax.validation.Valid;

/**
 * admin端
 * 教练接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/coach")
@AllArgsConstructor
@Api("教练管理")
public class AdminCoachController {
    
    private final CoachService coachService;

    /**
     * 列表
     */
    @GetMapping("")
    @ApiOperation("教练列表")
    public ResponseEntity<R> list(CoachSearchForm coachSearchForm){
        PageUtils page = coachService.queryPage(coachSearchForm);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 详情
     */
    @RequestMapping("/{coachId}")
    @ApiOperation("教练详情")
    public ResponseEntity<R> info(@PathVariable("coachId") Long coachId){
        CoachDetailDto coach = coachService.selectDetailWithTarget(coachId);
        return ResponseEntity.ok(new R(coach));
    }

    /**
     * 详情-带目标完成情况
     */
    @GetMapping("/target/{coachId}")
    @ApiOperation("教练详情-带目标完成情况")
    public ResponseEntity<R> infoWithTarget(@PathVariable("coachId") Long coachId){
        CoachDetailDto coachDetail = coachService.selectDetailWithTarget(coachId);
        return ResponseEntity.ok(new R(coachDetail));
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("更新教练")
    public ResponseEntity<R> update(@RequestBody CoachDetailDto coach){
		coachService.updateById(coach);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除教练")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
		coachService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 新增教练
     * @param form 教练表单
     * @return
     */
    @PostMapping()
    @ApiOperation("教练注册")
    public ResponseEntity<R> register(@RequestBody @Valid RegisterForm form) {
        coachService.register(form);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
