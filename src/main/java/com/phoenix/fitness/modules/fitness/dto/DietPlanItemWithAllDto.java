package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.DietPlanItemEntity;
import com.phoenix.fitness.modules.fitness.entity.HealthyMealEntity;
import com.phoenix.fitness.modules.fitness.entity.MealTypeEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DietPlanItemWithAllDto extends DietPlanItemEntity {
    /**
     * 餐食类型
     */
    @NotNull(message = "餐食类型不能为空")
    private MealTypeEntity mealType;
    /**
     * 健康餐
     */
    private HealthyMealEntity healthyMeal;
}
