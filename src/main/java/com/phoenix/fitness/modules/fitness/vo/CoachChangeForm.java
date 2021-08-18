package com.phoenix.fitness.modules.fitness.vo;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CoachChangeForm {

    @NotNull(message = "顾客id不能为空")
    private Long customerId;

    @NotNull(message = "教练id不能为空")
    private Long coachId;

}
