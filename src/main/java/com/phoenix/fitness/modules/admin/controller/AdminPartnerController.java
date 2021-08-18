package com.phoenix.fitness.modules.admin.controller;

import com.phoenix.fitness.modules.fitness.dto.PartnerWithGymsDto;
import com.phoenix.fitness.modules.fitness.service.PartnerService;
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
 * 合作伙伴接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("sys/partner")
@AllArgsConstructor
@Api("合作伙伴")
public class AdminPartnerController {

    private final PartnerService partnerService;

    /**
     * 列表
     */
    @GetMapping()
    @ApiOperation("合作伙伴列表")
    public ResponseEntity<R> list(@RequestParam Map<String, Object> params){
        PageUtils page = partnerService.queryPage(params);
        return ResponseEntity.ok(new R(page));
    }

    /**
     * 详情
     */
    @GetMapping("/{id}")
    @ApiOperation("合作伙伴详情")
    public ResponseEntity<R> info(@PathVariable("id") Long id){
        PartnerWithGymsDto partner = partnerService.getDetail(id);
        return ResponseEntity.ok(new R(partner));
    }

    /**
     * 保存
     */
    @PostMapping()
    @ApiOperation("新增合作伙伴")
    public ResponseEntity<R> save(@RequestBody @Valid PartnerWithGymsDto partner){
        partnerService.save(partner);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 修改
     */
    @PutMapping()
    @ApiOperation("修改合作伙伴")
    public ResponseEntity<R> update(@RequestBody @Valid PartnerWithGymsDto partner){
        partnerService.updateById(partner);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation("删除合作伙伴")
    public ResponseEntity<R> delete(@PathVariable("id") Long id){
        partnerService.removeByDeleteFlag(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
