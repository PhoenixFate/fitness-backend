package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 顾客打卡
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-01-11 10:58:06
 */
@Data
@TableName("tb_customer_clock_in")
public class CustomerClockInEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long customerClockInId;
    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 顾客编号
     */
    @NotNull(message = "顾客编号不能为空")
    private Long customerNumber;
    /**
     * 实体卡号
     */
    private String physicalCardNumber;
    /**
     * 打卡时间
     */
    private Date clockInTime;
    /**
     * 退卡时间
     */
    private Date clockOutTime;
    /**
     * 顾客姓名
     */
    @TableField(exist = false)
    private String customerName;
}
