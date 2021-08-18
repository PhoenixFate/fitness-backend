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
import com.phoenix.fitness.modules.fitness.dao.DietPlanDao;
import com.phoenix.fitness.modules.fitness.dao.DietPlanItemDao;
import com.phoenix.fitness.modules.fitness.dao.HealthyMealDao;
import com.phoenix.fitness.modules.fitness.dao.MealTypeDao;
import com.phoenix.fitness.modules.fitness.dto.DietPlanItemWithAllDto;
import com.phoenix.fitness.modules.fitness.dto.DietPlanWithItemsDto;
import com.phoenix.fitness.modules.fitness.entity.DietPlanEntity;
import com.phoenix.fitness.modules.fitness.entity.DietPlanItemEntity;
import com.phoenix.fitness.modules.fitness.entity.HealthyMealEntity;
import com.phoenix.fitness.modules.fitness.entity.MealTypeEntity;
import com.phoenix.fitness.modules.fitness.service.DietPlanItemService;
import com.phoenix.fitness.modules.fitness.service.DietPlanService;
import com.phoenix.fitness.modules.pad.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("dietPlanService")
@AllArgsConstructor
public class DietPlanServiceImpl extends ServiceImpl<DietPlanDao, DietPlanEntity> implements DietPlanService {

    private final DietPlanDao dietPlanDao;

    private final DietPlanItemDao dietPlanItemDao;

    private final DietPlanItemService dietPlanItemService;

    private final MealTypeDao mealTypeDao;

    private final HealthyMealDao healthyMealDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params, UserEntity user) {
        QueryWrapper<DietPlanEntity> dietPlanEntityQueryWrapper = new QueryWrapper<>();
        if (params.get("searchValue") != null) {
            dietPlanEntityQueryWrapper.like("diet_plan_name", params.get("searchValue"));
        }
        if (user != null) {
            if (user.getGymId() != null) {
                dietPlanEntityQueryWrapper.eq("gym_id", user.getGymId());
            }
            if (user.getPartnerId() != null) {
                dietPlanEntityQueryWrapper.eq("partner_id", user.getPartnerId());
            }
        }
        IPage<DietPlanEntity> page = this.page(
                new Query<DietPlanEntity>().getPage(params),
                dietPlanEntityQueryWrapper.eq("delete_flag", 0).orderByDesc("create_time")
        );
        List<DietPlanEntity> records = page.getRecords();
        List<DietPlanEntity> finalData = new ArrayList<>();
        for (DietPlanEntity temp : records) {
            DietPlanWithItemsDto dietPlanWithItemsDto = dietPlanDao.selectDietPlanWithDetail(temp.getDietPlanId());
            finalData.add(dietPlanWithItemsDto);
        }
        page.setRecords(finalData);
        return new PageUtils(page);
    }

    @Override
    public DietPlanWithItemsDto getDetail(Long id) {
        DietPlanWithItemsDto dietPlan = dietPlanDao.selectDietPlanWithDetail(id);
        if (dietPlan == null || dietPlan.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_QUERY_NOT_FOUND);
        }
        return dietPlan;
    }

    @Override
    @Transactional
    public boolean save(DietPlanWithItemsDto dietPlan) {
        int count = dietPlanDao.insert(dietPlan);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_SAVE_FAILURE);
        }
        this.saveOrUpdateBase(dietPlan);
        return true;
    }

    @Override
    @Transactional
    public boolean updateById(DietPlanWithItemsDto dietPlan) {
        DietPlanEntity temp = dietPlanDao.selectById(dietPlan.getDietPlanId());
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_UPDATE_NOT_FOUND);
        }
        int count = dietPlanDao.updateById(dietPlan);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
        this.saveOrUpdateBase(dietPlan);
        return true;
    }

    public void saveOrUpdateBase(DietPlanWithItemsDto dietPlan) {
        dietPlanItemDao.delete(new QueryWrapper<DietPlanItemEntity>().eq("diet_plan_id", dietPlan.getDietPlanId()));
        if (!CollectionUtils.isEmpty(dietPlan.getDietPlanItems())) {
            List<DietPlanItemEntity> dietPlanItemList = new ArrayList<>();
            for (DietPlanItemWithAllDto dietPlanItemWithAllDto : dietPlan.getDietPlanItems()) {
                //检查餐食类型是否正确
                MealTypeEntity mealType = mealTypeDao.selectById(dietPlanItemWithAllDto.getMealType().getMealTypeId());
                if (mealType == null || mealType.getDeleteFlag().equals(1)) {
                    //餐食类型不正确
                    throw new FitnessException(ExceptionEnum.MEAL_TYPE_NOT_FOUND);
                }
                if (dietPlanItemWithAllDto.getHealthyMeal() != null && dietPlanItemWithAllDto.getHealthyMeal().getHealthyMealId() != null) {
                    //检查健康餐是否正确
                    HealthyMealEntity healthyMeal = healthyMealDao.selectById(dietPlanItemWithAllDto.getHealthyMeal().getHealthyMealId());
                    // if (healthyMeal == null || healthyMeal.getDeleteFlag().equals(1)) {
                    //     //健康餐不正确
                    //     throw new FitnessException(ExceptionEnum.HEALTHY_MEAL_NOT_FOUND);
                    // }
                    dietPlanItemWithAllDto.setHealthyMealId(healthyMeal.getHealthyMealId());
                }
                dietPlanItemWithAllDto.setMealTypeId(mealType.getMealTypeId());
                dietPlanItemWithAllDto.setDietPlanId(dietPlan.getDietPlanId());
                dietPlanItemList.add(dietPlanItemWithAllDto);
            }
            dietPlanItemService.saveOrUpdateBatch(dietPlanItemList);
        }
    }

    @Override
    public void removeByDeleteFlag(Long id) {
        DietPlanEntity temp = dietPlanDao.selectById(id);
        if (temp == null || temp.getDeleteFlag().equals(1)) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_NOT_FOUND);
        }
        Integer count = dietPlanDao.deleteByDeleteFlag(id);
        if (count == 0) {
            throw new FitnessException(ExceptionEnum.ITEM_DELETE_FAILURE);
        }
    }
}