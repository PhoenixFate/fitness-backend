package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.dto.CoachDetailDto;
import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.service.CoachService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.common.validator.ValidatorUtils;
import com.phoenix.fitness.modules.pad.annotation.Login;
import com.phoenix.fitness.modules.pad.annotation.LoginUser;
import com.phoenix.fitness.modules.pad.entity.UserEntity;
import com.phoenix.fitness.modules.pad.form.LoginForm;
import com.phoenix.fitness.modules.pad.form.PasswordForm;
import com.phoenix.fitness.modules.pad.service.UserService;
import com.phoenix.fitness.modules.pad.utils.JwtUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * pad端
 * 教练接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("pad/coach")
@AllArgsConstructor
@Api("教练管理")
public class CoachController {
    
    private final CoachService coachService;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    /**
     * 登录
     */
    @PostMapping("login")
    @ApiOperation("登录")
    public ResponseEntity<R> login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        //用户登录
        UserEntity user = userService.login(form);
        //生成token
        String token = jwtUtils.generateToken(user);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("expire", jwtUtils.getExpire());
        map.put("userId",user.getUserId());
        return ResponseEntity.ok(new R(map));
    }

    /**
     * 详情
     */
    @GetMapping("/{coachId}")
    @ApiOperation("教练详情")
    public ResponseEntity<R> detail(@PathVariable("coachId") Long coachId){
        CoachEntity coachDetail = coachService.getById(coachId);
        return ResponseEntity.ok(new R(coachDetail));
    }

    /**
     * 详情-带目标完成情况
     */
    @GetMapping("/target/{coachId}")
    @ApiOperation("教练详情-带目标完成情况")
    public ResponseEntity<R> info(@PathVariable("coachId") Long coachId){
        CoachDetailDto coachDetail = coachService.selectDetailWithTarget(coachId);
        return ResponseEntity.ok(new R(coachDetail));
    }

    /**
     * 信息
     */
    @GetMapping("/user/{userId}")
    @ApiOperation("根据userId查询教练信息")
    public ResponseEntity<R> infoByUserId(@PathVariable("userId") Long userId){
        CoachEntity coach = coachService.getByUserId(userId);
        return ResponseEntity.ok(new R(coach));
    }


    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("更新教练")
    public ResponseEntity<R> update(@RequestBody CoachEntity coach){
		coachService.updateById(coach);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 修改密码
     */
    @Login
    @PutMapping("/password")
    @ApiOperation("修改密码")
    public ResponseEntity<R> changePassword(@RequestBody PasswordForm passwordForm,@LoginUser UserEntity user){
        coachService.changePassword(passwordForm,null);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
