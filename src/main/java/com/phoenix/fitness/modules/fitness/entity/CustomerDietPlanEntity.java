package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.phoenix.fitness.modules.fitness.dto.DietPlanWithItemsDto;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_customer_diet_plan_relation")
public class CustomerDietPlanEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerDietPlanRelationId;
    /**
     * 顾客id
     */
    @NotNull(message = "顾客id不能为空")
    private Long customerId;
    /**
     * 饮食计划id
     */
    @NotNull(message = "饮食计划id不能为空")
    private Long dietPlanId;
    /**
     * 注意事项
     */
    private String attention;
    /**
     * 客户计划开始日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    /**
     * 客户计划开始日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endDate;
    /**
     * 是否是最新
     */
    private Integer isLatest;
    /**
     * 添加的日期
     */
    private Date addTime;

    @TableField(exist = false)
    private DietPlanWithItemsDto dietPlan;
}
