package com.phoenix.fitness.modules.wechat.controller;

import com.phoenix.fitness.modules.fitness.dto.CustomerPlanDayWithClassInfoDto;
import com.phoenix.fitness.modules.fitness.service.CoachService;
import com.phoenix.fitness.modules.fitness.vo.CoachDateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.modules.admin.form.CoachSearchForm;

import java.util.List;

/**
 * 小程序端
 * 教练接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("wechat/coach")
@AllArgsConstructor
@Api("教练管理")
public class WechatCoachController {
    
    private final CoachService coachService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("教练列表")
    public ResponseEntity<R> list(CoachSearchForm coachSearchForm){
        PageUtils page = coachService.queryPage(coachSearchForm);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 查询某一天教练的安排
     */
    @PostMapping("/day")
    @ApiOperation("查询某一天教练的安排")
    public ResponseEntity<R> infoByNumber(@RequestBody CoachDateVO coachDateVO){
        List<CustomerPlanDayWithClassInfoDto> customerPlanDayList = coachService.oneDayList(coachDateVO);
        return ResponseEntity.ok(new R(customerPlanDayList));
    }



}
