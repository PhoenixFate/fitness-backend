package com.phoenix.fitness.modules.fitness.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CoachDateVO {

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "日期不能为空")
    private Date date;

    @NotNull(message = "coachId不能为空")
    private Integer coachId;

}
