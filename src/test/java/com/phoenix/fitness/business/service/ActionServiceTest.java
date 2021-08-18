package com.phoenix.fitness.business.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.ActionDao;
import com.phoenix.fitness.modules.fitness.dao.ActionTypeDao;
import com.phoenix.fitness.modules.fitness.dao.EquipmentDao;
import com.phoenix.fitness.modules.fitness.dto.ActionWithAllDto;
import com.phoenix.fitness.modules.fitness.entity.ActionTypeEntity;
import com.phoenix.fitness.modules.fitness.entity.EquipmentEntity;
import com.phoenix.fitness.modules.fitness.service.ActionService;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 动作service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ActionServiceTest {

    @Autowired
    private ActionDao actionDao;

    @Autowired
    private ActionService actionService;

    @Autowired
    private ActionTypeDao actionTypeDao;

    @Autowired
    private EquipmentDao equipmentDao;

    /**
     * 动作-列表
     */
    @Test
    public void actionListTest(){
        PageUtils pageUtils = actionService.queryPage(new HashMap<>(),new UserEntity());
        Assert.assertNotNull(pageUtils.getList());
    }

    /**
     * 动作-详情
     */
    @Test
    public void actionDetailTest(){
        ActionWithAllDto action = actionService.getDetail(3L);
        System.out.println(action);
        Assert.assertNotNull(action);
    }

    /**
     * 动作-新增
     */
    @Test
    public void actionSaveTest(){
        ActionWithAllDto action=new ActionWithAllDto();
        action.setActionName("测试动作");
        action.setActionDescription("动作描述");
        action.setContainWeight(1);
        ActionTypeEntity actionType = actionTypeDao.selectById(3L);
        action.setActionType(actionType);
        List<String> examples=new ArrayList<>();
        examples.add("https://fitness-coach.oss-cn-hangzhou.aliyuncs.com/action/%E5%8D%95%E8%85%BF%E6%9D%A0%E9%93%83%E6%B7%B1%E8%B9%B21.jpg");
        action.setActionExamples(examples);
        EquipmentEntity equipment = equipmentDao.selectById(12L);
        List<EquipmentEntity> equipments=new ArrayList<>();
        equipments.add(equipment);
        action.setEquipments(equipments);
        Boolean flag = actionService.save(action);
        Assert.assertTrue(flag);
    }

    /**
     * 动作-修改
     */
    @Test
    public void actionUpdateTest(){
        ActionWithAllDto action = actionDao.selectActionWithAll(20L);
        action.setActionName("新测试");
        Boolean flag = actionService.updateById(action);
        Assert.assertTrue(flag);
    }

    /**
     * 动作-删除
     */
    @Test
    public void actionDeleteTest(){
        Boolean flag = actionService.removeByDeleteFlag(20L);
        Assert.assertTrue(flag);
    }


}
