package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.dto.ClassInfoWithExercisesDto;
import com.phoenix.fitness.modules.fitness.service.ClassInfoService;
import com.phoenix.fitness.modules.pad.annotation.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.annotation.Login;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * pad端
 * 课程信息接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("pad/classInfo")
@AllArgsConstructor
@Api("课程管理")
public class ClassInfoController {
    
    private final ClassInfoService classInfoService;

    /**
     * 列表
     */
    @Login
    @GetMapping()
    @ApiOperation("课程列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params,@LoginUser UserEntity user){
        PageUtils page = classInfoService.queryPage(params,null);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 列表-带详情
     */
    @Login
    @GetMapping("/detail")
    @ApiOperation("带详情的课程列表")
    public ResponseEntity<R> list(@LoginUser UserEntity user){
        List<ClassInfoWithExercisesDto> classInfoEntityList= classInfoService.selectListWithDetail(null);
        return ResponseEntity.ok(new R(classInfoEntityList));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("课程详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
        ClassInfoWithExercisesDto classInfo = classInfoService.getDetail(id);
        return ResponseEntity.ok(new R(classInfo));
    }

    /**
     * 保存
     */
    @Login
    @PostMapping()
    @ApiOperation("新增课程")
    public ResponseEntity<R> save(@RequestBody @Valid ClassInfoWithExercisesDto classInfo,@LoginUser UserEntity user){
        classInfo.setGymId(user.getGymId());
        classInfo.setPartnerId(user.getPartnerId());
		classInfoService.save(classInfo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改课程")
    public ResponseEntity<R> update(@RequestBody @Valid ClassInfoWithExercisesDto classInfo){
		classInfoService.updateById(classInfo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除课程")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
		classInfoService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
