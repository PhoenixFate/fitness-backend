package com.phoenix.fitness.modules.fitness.dto;

import com.phoenix.fitness.modules.fitness.entity.DietPlanEntity;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class DietPlanWithItemsDto extends DietPlanEntity {
    /**
     * 健康餐子项
     */
    @Valid
    private List<DietPlanItemWithAllDto> dietPlanItems;
}
