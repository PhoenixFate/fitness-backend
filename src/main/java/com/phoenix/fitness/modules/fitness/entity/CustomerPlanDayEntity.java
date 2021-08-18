package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * 顾客具体的某一天训练
 * 顾客的训练计划会生成一系列某天的训练
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_customer_plan_day")
public class CustomerPlanDayEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerPlanDayId;
    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 关联的顾客训练计划id
     */
    private Long customerPlanId;
    /**
     * 关联的顾客训练计划阶段id
     */
    private Long customerPlanPeriodId;
    /**
     * 关联的顾客训练阶段下面的训练周id
     */
    private Long customerPlanWeekId;
    /**
     * 当天是一周中的第几天
     * 周日为0
     * 周一为1
     * 以此类推
     */
    private Integer dayOfWeek;
    /**
     * 具体日期
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date detailDate;
    /**
     * 关联课程
     */
    private Long classId;
    /**
     * 状态
     * 0 未完成
     * 1 已完成
     */
    private Integer status;
    /**
     * 是否激活
     * 0 未激活
     * 1 已激活
     */
    private Integer isActive;
    /**
     * 是否支付
     * 1 已支付
     * 0 未支付
     */
    private Integer isPay;
    /**
     * 是否是休息
     * 1 是休息
     * 0 不休息（不休息是健身）
     */
    private Integer isRest;
    /**
     * 顾客是否确认
     */
    private Integer customerIsSure;
    /**
     * 顾客评价的分数
     */
    private Double customerCommentScore;
    /**
     * 顾客评价的内容
     */
    private String customerCommentContent;
    /**
     * 当日课程已经使用的时间
     */
    private Integer usedMinute;
    /**
     * 当日课程已经使用的时间
     */
    private Integer usedSecond;
    /**
     * 当日训练的负责教练
     */
    private Long coachId;
    /**
     * 当日训练的开始时间
     */
    private Time startTime;
    /**
     * 当日训练的结束时间
     */
    private Time endTime;

}
