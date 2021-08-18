package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.dto.ActionTypeWithActionsDto;
import com.phoenix.fitness.modules.fitness.dto.ActionWithAllDto;
import com.phoenix.fitness.modules.fitness.service.ActionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.pad.annotation.Login;
import com.phoenix.fitness.modules.pad.annotation.LoginUser;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * pad端
 * 动作接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@RestController
@RequestMapping("/pad/action")
@Api("动作管理")
@AllArgsConstructor
public class ActionController {

    private final ActionService actionService;

    /**
     * 列表
     */
    @Login
    @GetMapping()
    @ApiOperation("动作列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params,@LoginUser UserEntity user){
        PageUtils page = actionService.queryPage(params,null);
        return ResponseEntity.ok(new R(page));
    }

    @Login
    @GetMapping("/type")
    @ApiOperation("已经分好类别的动作列表")
    public ResponseEntity<R> listWithType(@RequestParam Map<String, Object> params,@LoginUser UserEntity user){
        List<ActionTypeWithActionsDto> actionTypeWithActions = actionService.getActionsWithType(params,null);
        return ResponseEntity.ok(new R(actionTypeWithActions));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("动作详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
		ActionWithAllDto action = actionService.getDetail(id);
        return ResponseEntity.ok(new R(action));
    }

    /**
     * 保存
     */
    @Login
    @PostMapping()
    @ApiOperation("新增动作")
    public ResponseEntity<R> save(@RequestBody @Valid ActionWithAllDto action,@LoginUser UserEntity user){
        action.setGymId(user.getGymId());
        action.setPartnerId(user.getPartnerId());
		actionService.save(action);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改动作")
    public ResponseEntity<R> update(@RequestBody @Valid ActionWithAllDto action){
		actionService.updateById(action);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除动作")
    public ResponseEntity<R> delete(@PathVariable("id")Long id){
		actionService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
