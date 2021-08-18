package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("tb_customer_add_history")
public class CustomerAddHistoryEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerAddHistoryId;
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 教练id
     */
    private Long coachId;
    /**
     * 添加顾客的日期
     */
    private Date addDate;
    /**
     * 时间段
     */
    private String period;
    /**
     * 添加顾客的时间
     */
    private Date addTime;
}
