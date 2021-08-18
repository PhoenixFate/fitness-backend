package com.phoenix.fitness.business.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.EquipmentDao;
import com.phoenix.fitness.modules.fitness.entity.EquipmentEntity;
import com.phoenix.fitness.modules.fitness.service.EquipmentService;
import java.util.HashMap;

/**
 * 设备service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class EquipmentServiceTest {

    @Autowired
    private EquipmentDao equipmentDao;
    
    @Autowired
    private EquipmentService equipmentService;
    
    /**
     * 动作分类-列表
     */
    @Test
    public void equipmentListTest(){
        PageUtils pageUtils = equipmentService.queryPage(new HashMap<>());
        Assert.assertNotNull(pageUtils.getList());
    }

    /**
     * 动作分类-详情
     */
    @Test
    public void equipmentDetailTest(){
        EquipmentEntity equipment = equipmentService.getById(3);
        Assert.assertNotNull(equipment);
    }

    /**
     * 动作分类-新增
     */
    @Test
    public void equipmentSaveTest(){
        EquipmentEntity equipment=new EquipmentEntity();
        equipment.setEquipmentName("测试器材");
        equipment.setEquipmentImageUrl("bbb");
        boolean flag = equipmentService.save(equipment);
        Assert.assertTrue(flag);
    }

    /**
     * 动作分类-修改
     */
    @Test
    public void equipmentUpdateTest(){
        EquipmentEntity equipment = equipmentDao.selectById(3L);
        equipment.setEquipmentName("新测试hh");
        boolean flag = equipmentService.updateById(equipment);
        Assert.assertTrue(flag);
    }

    /**
     * 动作分类-删除
     */
    @Test
    public void equipmentDeleteTest(){
        Boolean flag = equipmentService.removeByDeleteFlag(3L);
        Assert.assertTrue(flag);
    }


}
