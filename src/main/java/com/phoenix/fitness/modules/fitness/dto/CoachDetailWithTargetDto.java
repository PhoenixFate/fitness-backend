package com.phoenix.fitness.modules.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class CoachDetailWithTargetDto {
    private static final long serialVersionUID = 1L;

    /**
     * targetId;
     */
    private Long targetId;

    private Long targetCoachRelationId;
    /**
     *
     */
    private String targetName;

    private String targetDescription;
    /**
     *
     */
    private String targetType;
    /**
     * 具体的指标
     */
    private Double detailNumber;
    /**
     * 1日计划、2周计划、3月计划、10自定义计划
     */
    private String targetPeriodType;
    /**
     * 自定义的开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date startDate;
    /**
     * 自定义的结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date endDate;

    /**
     * 教练当前已完成的目标
     */
    private String coachFinishedNumber;


}
