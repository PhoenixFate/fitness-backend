package com.phoenix.fitness.business.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.OrderWithItemsDto;
import com.phoenix.fitness.modules.fitness.service.OrderService;
import java.util.HashMap;

/**
 * 订单service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    
    /**
     * 订单-列表
     */
    @Test
    public void orderListTest(){
        PageUtils pageUtils = orderService.queryPage(new HashMap<>());
        Assert.assertNotNull(pageUtils.getList());
    }

    /**
     * 订单-详情
     */
    @Test
    public void orderDetailTest(){
        OrderWithItemsDto order = orderService.getDetail(3L);
        Assert.assertNotNull(order);
    }

    /**
     * 订单-详情-根据订单编号查询
     */
    @Test
    public void orderDetailWithNumberTest(){
        OrderWithItemsDto order = orderService.getDetailByOrderNumber("202102051649572147826241");
        Assert.assertNotNull(order);
    }

}
