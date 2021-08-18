package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.phoenix.fitness.common.utils.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.fitness.dao.HealthyMealDao;
import com.phoenix.fitness.modules.fitness.dao.HealthyMealItemDao;
import com.phoenix.fitness.modules.fitness.dto.HealthyMealWithItemsDto;
import com.phoenix.fitness.modules.fitness.entity.HealthyMealEntity;
import com.phoenix.fitness.modules.fitness.entity.HealthyMealItemEntity;
import com.phoenix.fitness.modules.fitness.service.HealthyMealItemService;
import com.phoenix.fitness.modules.fitness.service.HealthyMealService;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("healthyMealService")
@AllArgsConstructor
public class HealthyMealServiceImpl extends ServiceImpl<HealthyMealDao, HealthyMealEntity> implements HealthyMealService {

    private final HealthyMealDao healthyMealDao;

    private final HealthyMealItemDao healthyMealItemDao;

    private final HealthyMealItemService healthyMealItemService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, UserEntity user) {
        QueryWrapper<HealthyMealEntity> healthyMealEntityQueryWrapper = new QueryWrapper<>();
        if(params.get("searchValue")!=null){
            healthyMealEntityQueryWrapper.like("healthy_meal_name",params.get("searchValue"));
        }
        if(user!=null){
            if(user.getGymId()!=null){
                healthyMealEntityQueryWrapper.eq("gym_id",user.getGymId());
            }
            if(user.getPartnerId()!=null){
                healthyMealEntityQueryWrapper.eq("partner_id",user.getPartnerId());
            }
        }
        IPage<HealthyMealEntity> page = this.page(
                new Query<HealthyMealEntity>().getPage(params),
                healthyMealEntityQueryWrapper.eq("delete_flag",0).orderByDesc("create_time")
        );
        List<HealthyMealEntity> records = page.getRecords();
        List<HealthyMealEntity> finalData=new ArrayList<>();
        for(HealthyMealEntity temp:records){
            HealthyMealWithItemsDto healthyMealWithDetail = healthyMealDao.getHealthyMealWithDetail(temp.getHealthyMealId());
            finalData.add(healthyMealWithDetail);
        }
        page.setRecords(finalData);
        return new PageUtils(page);
    }

    @Override
    public List<HealthyMealWithItemsDto> listWithDetail(UserEntity user) {
        return healthyMealDao.getHealthyMealListWithDetail(user);
    }

    @Override
    public void removeByDeleteFlag(Long id) {
        HealthyMealWithItemsDto temp=healthyMealDao.getHealthyMealWithDetail(id);
        if(temp==null || temp.getDeleteFlag().equals(1)){
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count=healthyMealDao.deleteByDeleteFlag(id);
        if(count==0){
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
    }

    @Override
    public HealthyMealWithItemsDto getDetail(Long id) {
        HealthyMealWithItemsDto healthyMeal=healthyMealDao.getHealthyMealWithDetail(id);
        if(healthyMeal==null || healthyMeal.getDeleteFlag().equals(1)){
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return healthyMeal;
    }

    @Override
    @Transactional
    public boolean save(HealthyMealWithItemsDto healthyMeal) {
        int count = healthyMealDao.insert(healthyMeal);
        if(count==0){
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(healthyMeal);
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(HealthyMealWithItemsDto healthyMeal) {
        HealthyMealEntity temp = healthyMealDao.selectById(healthyMeal.getHealthyMealId());
        if(temp==null || temp.getDeleteFlag().equals(1)){
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = healthyMealDao.updateById(healthyMeal);
        if(count==0){
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_FAILURE);
        }
        this.saveOrUpdateBase(healthyMeal);
        return true;
    }

    public void saveOrUpdateBase(HealthyMealWithItemsDto healthyMeal){
        //删除原有的健康餐子项
        healthyMealItemDao.delete(new QueryWrapper<HealthyMealItemEntity>().eq("healthy_meal_id",healthyMeal.getHealthyMealId()));
        if(!CollectionUtils.isEmpty(healthyMeal.getHealthyMealItems())){
            for(HealthyMealItemEntity healthyMealItemEntity:healthyMeal.getHealthyMealItems()){
                healthyMealItemEntity.setHealthyMealId(healthyMeal.getHealthyMealId());
            }
            healthyMealItemService.saveBatch(healthyMeal.getHealthyMealItems());
        }
    }

}