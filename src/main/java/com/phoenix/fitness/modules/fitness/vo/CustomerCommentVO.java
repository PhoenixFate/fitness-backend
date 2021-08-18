package com.phoenix.fitness.modules.fitness.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CustomerCommentVO {
    /**
     * 顾客id
     */
    @NotNull(message = "顾客id不能为空")
    private Long customerId;
    /**
     * 顾客具体哪一天的训练日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "具体训练日不能为空")
    private Date detailDate;
    /**
     * 顾客评价的分数
     */
    private Double commentScore;
    /**
     * 顾客评价的内容
     */
    private String commentContent;
}
