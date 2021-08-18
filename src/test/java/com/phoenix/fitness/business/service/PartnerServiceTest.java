package com.phoenix.fitness.business.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dto.PartnerWithGymsDto;
import com.phoenix.fitness.modules.fitness.service.PartnerService;
import java.util.HashMap;

/**
 * 合作伙伴service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PartnerServiceTest {

    @Autowired
    private PartnerService partnerService;
    
    /**
     * 合作伙伴-列表
     */
    @Test
    public void partnerListTest(){
        PageUtils pageUtils = partnerService.queryPage(new HashMap<>());
        Assert.assertNotNull(pageUtils.getList());
    }

    /**
     * 合作伙伴-详情
     */
    @Test
    public void partnerDetailTest(){
        PartnerWithGymsDto partner = partnerService.getDetail(2L);
        Assert.assertNotNull(partner);
    }

    /**
     * 合作伙伴-新增
     */
    @Test
    public void partnerSaveTest(){
        PartnerWithGymsDto partner=new PartnerWithGymsDto();
        partner.setPartnerName("测试");
        Boolean flag = partnerService.save(partner);
        Assert.assertTrue(flag);
    }

    /**
     * 合作伙伴-修改
     */
    @Test
    public void partnerUpdateTest(){
        PartnerWithGymsDto partner = partnerService.getDetail(2L);
        partner.setPartnerName("新测试hh");
        Boolean flag = partnerService.updateById(partner);
        Assert.assertTrue(flag);
    }

    /**
     * 合作伙伴-删除
     */
    @Test
    public void partnerDeleteTest(){
        Boolean flag = partnerService.removeByDeleteFlag(2L);
        Assert.assertTrue(flag);
    }


}
