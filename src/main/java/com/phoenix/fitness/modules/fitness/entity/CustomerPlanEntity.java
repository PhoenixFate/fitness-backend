package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_customer_plan")
public class CustomerPlanEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerPlanId;
    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 教练id
     */
    private Long coachId;
    /**
     * 训练计划id
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long trainingPlanId;
    /**
     * 客户计划开始时间
     * 例如：15：00
     */
    private Time startTime;
    /**
     * 客户计划结束时间
     * 例如 17：00
     */
    private Time endTime;
    /**
     * 客户计划开始日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startDate;
    /**
     * 计划状态
     */
    private Integer planStatus;
    /**
     * 是否是最新
     */
    private Integer isLatest;
    /**
     * 总的课程数量
     */
    private Integer totalClassCount;
    /**
     * 已完成的课程数量
     */
    private Integer finishedClassCount;
}
