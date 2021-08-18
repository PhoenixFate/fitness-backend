package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.entity.VipCardEntity;
import com.phoenix.fitness.modules.fitness.service.VipCardService;
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
 * 会员卡接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/vipCard")
@AllArgsConstructor
@Api("会员卡管理")
public class AdminVipCardController {

    private final VipCardService vipCardService;

    /**
     * 会员卡列表
     */
    @GetMapping()
    @ApiOperation("会员卡列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = vipCardService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 会员卡详情
     */
    @GetMapping("/{id}")
    @ApiOperation("会员卡详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
		VipCardEntity vipCard = vipCardService.getById(id);
        return ResponseEntity.ok(new R(vipCard));
    }

    /**
     * 保存会员卡
     */
    @PostMapping()
    @ApiOperation("新增会员卡")
    public ResponseEntity<R> save(@RequestBody @Valid VipCardEntity vipCard){
        vipCardService.save(vipCard);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改会员卡
     */
    @PutMapping()
    @ApiOperation("修改会员卡")
    public ResponseEntity<R> update(@RequestBody @Valid VipCardEntity vipCard){
        vipCardService.updateById(vipCard);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除会员卡
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除会员卡")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
        vipCardService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
