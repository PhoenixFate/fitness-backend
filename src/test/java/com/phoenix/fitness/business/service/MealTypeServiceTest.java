package com.phoenix.fitness.business.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.MealTypeDao;
import com.phoenix.fitness.modules.fitness.entity.MealTypeEntity;
import com.phoenix.fitness.modules.fitness.service.MealTypeService;
import java.util.HashMap;

/**
 * 餐食类型service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MealTypeServiceTest {

    @Autowired
    private MealTypeDao mealTypeDao;
    
    @Autowired
    private MealTypeService mealTypeService;
    
    /**
     * 动作分类-列表
     */
    @Test
    public void mealTypeListTest(){
        PageUtils pageUtils = mealTypeService.queryPage(new HashMap<>());
        Assert.assertNotNull(pageUtils.getList());
    }

    /**
     * 动作分类-详情
     */
    @Test
    public void mealTypeDetailTest(){
        MealTypeEntity mealType = mealTypeService.getById(3);
        Assert.assertNotNull(mealType);
    }

    /**
     * 动作分类-新增
     */
    @Test
    public void mealTypeSaveTest(){
        MealTypeEntity mealType=new MealTypeEntity();
        mealType.setMealTypeName("测试");
        mealType.setMealTypeValue("abc");
        boolean flag = mealTypeService.save(mealType);
        Assert.assertTrue(flag);
    }

    /**
     * 动作分类-修改
     */
    @Test
    public void mealTypeUpdateTest(){
        MealTypeEntity mealType = mealTypeDao.selectById(2L);
        mealType.setMealTypeName("新测试hh");
        boolean flag = mealTypeService.updateById(mealType);
        Assert.assertTrue(flag);
    }

    /**
     * 动作分类-删除
     */
    @Test
    public void mealTypeDeleteTest(){
        Boolean flag = mealTypeService.removeByDeleteFlag(3L);
        Assert.assertTrue(flag);
    }


}
