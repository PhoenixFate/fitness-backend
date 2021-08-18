package com.phoenix.fitness.business.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.GymDao;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import com.phoenix.fitness.modules.fitness.service.GymService;
import java.util.HashMap;

/**
 * 健身房service层测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GymServiceTest {

    @Autowired
    private GymDao gymDao;
    
    @Autowired
    private GymService gymService;
    
    /**
     * 健身房-列表
     */
    @Test
    public void gymListTest(){
        PageUtils pageUtils = gymService.queryPage(new HashMap<>());
        Assert.assertNotNull(pageUtils.getList());
    }

    /**
     * 健身房-详情
     */
    @Test
    public void gymDetailTest(){
        GymEntity gym = gymService.getById(3);
        Assert.assertNotNull(gym);
    }

    /**
     * 健身房-新增
     */
    @Test
    public void gymSaveTest(){
        GymEntity gym=new GymEntity();
        gym.setGymName("测试");
        boolean flag = gymService.save(gym);
        Assert.assertTrue(flag);
    }

    /**
     * 健身房-修改
     */
    @Test
    public void gymUpdateTest(){
        GymEntity gym = gymDao.selectById(2L);
        gym.setGymName("新测试hh");
        boolean flag = gymService.updateById(gym);
        Assert.assertTrue(flag);
    }

    /**
     * 健身房-删除
     */
    @Test
    public void gymDeleteTest(){
        Boolean flag = gymService.removeByDeleteFlag(3L);
        Assert.assertTrue(flag);
    }


}
