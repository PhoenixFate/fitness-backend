package com.phoenix.fitness.business.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.ActionTypeDao;
import com.phoenix.fitness.modules.fitness.entity.ActionTypeEntity;
import com.phoenix.fitness.modules.fitness.service.ActionTypeService;
import java.util.HashMap;

/**
 * 动作分类service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ActionTypeServiceTest {

    @Autowired
    private ActionTypeDao actionTypeDao;

    @Autowired
    private ActionTypeService actionTypeService;

    /**
     * 动作分类-列表
     */
    @Test
    public void actionTypeListTest(){
        PageUtils pageUtils = actionTypeService.queryPage(new HashMap<>());
        Assert.assertNotNull(pageUtils.getList());
    }

    /**
     * 动作分类-详情
     */
    @Test
    public void actionTypeDetailTest(){
        ActionTypeEntity actionType = actionTypeService.getById(3);
        Assert.assertNotNull(actionType);
    }

    /**
     * 动作分类-新增
     */
    @Test
    public void actionTypeSaveTest(){
        ActionTypeEntity actionType=new ActionTypeEntity();
        actionType.setActionTypeName("测试分类名");
        actionType.setActionTypeImageUrl("sss");
        Boolean flag = actionTypeService.save(actionType);
        Assert.assertTrue(flag);
    }

    /**
     * 动作分类-修改
     */
    @Test
    public void actionTypeUpdateTest(){
        ActionTypeEntity actionType = actionTypeDao.selectById(3L);
        actionType.setActionTypeName("新测试hh");
        boolean flag = actionTypeService.updateById(actionType);
        Assert.assertTrue(flag);
    }

    /**
     * 动作分类-删除
     */
    @Test
    public void actionTypeDeleteTest(){
        Boolean flag = actionTypeService.removeByDeleteFlag(3L);
        Assert.assertTrue(flag);
    }


}
