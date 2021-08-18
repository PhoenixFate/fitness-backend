package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.HealthyMealEntity;
import com.phoenix.fitness.modules.fitness.entity.HealthyMealItemEntity;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * 健康餐dto，带健康餐子项
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
public class HealthyMealWithItemsDto extends HealthyMealEntity {
    /**
     * 健康餐子项
     */
    @Valid
    private List<HealthyMealItemEntity> healthyMealItems;
}
