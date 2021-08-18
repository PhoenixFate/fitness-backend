package com.phoenix.fitness.modules.fitness.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TargetDeleteCoachVO {

    /**
     * 目标id
     */
    @TableId
    @NotNull(message = "目标id不能为空")
    private Long targetId;

    /**
     * 目标关联的教练列表
     */
    @NotNull(message = "要删除的教练不能为空")
    private CoachEntity coach;
}
