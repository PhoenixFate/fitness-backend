package com.phoenix.fitness.business.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.VipCardDao;
import com.phoenix.fitness.modules.fitness.entity.VipCardEntity;
import com.phoenix.fitness.modules.fitness.service.VipCardService;
import java.util.HashMap;

/**
 * 会员卡service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class VipCardServiceTest {

    @Autowired
    private VipCardDao vipCardDao;
    
    @Autowired
    private VipCardService vipCardService;
    
    /**
     * 会员卡-列表
     */
    @Test
    public void vipCardListTest(){
        PageUtils pageUtils = vipCardService.queryPage(new HashMap<>());
        Assert.assertNotNull(pageUtils.getList());
    }

    /**
     * 会员卡-详情
     */
    @Test
    public void vipCardDetailTest(){
        VipCardEntity vipCard = vipCardService.getById(3);
        Assert.assertNotNull(vipCard);
    }

    /**
     * 会员卡-新增
     */
    @Test
    public void vipCardSaveTest(){
        VipCardEntity vipCard=new VipCardEntity();
        vipCard.setVipCardName("测试");
        vipCard.setVipCardImage("sss");
        vipCard.setVipCardType("WEEK_CARD");
        vipCard.setAddVipDays(30);
        boolean flag = vipCardService.save(vipCard);
        Assert.assertTrue(flag);
    }

    /**
     * 会员卡-修改
     */
    @Test
    public void vipCardUpdateTest(){
        VipCardEntity vipCard = vipCardDao.selectById(2L);
        vipCard.setVipCardName("新测试hh");
        boolean flag = vipCardService.updateById(vipCard);
        Assert.assertTrue(flag);
    }

    /**
     * 会员卡-删除
     */
    @Test
    public void vipCardDeleteTest(){
        Boolean flag = vipCardService.removeByDeleteFlag(3L);
        Assert.assertTrue(flag);
    }


}
