package com.phoenix.fitness.modules.pad.controller;

import com.phoenix.fitness.modules.fitness.entity.OrderEntity;
import com.phoenix.fitness.modules.fitness.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * pad端
 * 订单接口
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@RestController
@RequestMapping("pad/order")
@RequiredArgsConstructor
@Api("订单管理")
public class OrderController {

    private final OrderService orderService;

    @Value("${fitness.pay.qrCodePath}")
    private String qrCodePath;

    /**
     * 根据orderNumber获取详情
     */
    @GetMapping("/number/{orderNumber}")
    @ApiOperation("订单详情")
    public ResponseEntity<R> infoByOrderNumber(@PathVariable("orderNumber") String orderNumber){
        OrderEntity order = orderService.getDetailByOrderNumber(orderNumber);
        return ResponseEntity.ok(new R(order));
    }

    /**
     * IO流读取图片 by:long
     * @return
     */
    @GetMapping(value = "/qrCode/{imgName}")
    public String IoReadImage(@PathVariable String imgName, HttpServletResponse response) throws IOException {
        ServletOutputStream out = null;
        FileInputStream ips = null;
        try {
            //获取图片存放路径
            String imgPath = qrCodePath + imgName;
            ips = new FileInputStream(imgPath);
            response.setContentType("multipart/form-data");
            out = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                out.write(buffer,0,len);
            }
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            ips.close();
        }
        return null;
    }

}
