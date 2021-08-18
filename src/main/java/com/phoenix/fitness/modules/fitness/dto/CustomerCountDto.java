package com.phoenix.fitness.modules.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
public class CustomerCountDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date detailDate;

    private Integer customerCount;

}
