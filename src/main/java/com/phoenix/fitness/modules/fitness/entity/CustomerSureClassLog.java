package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 销课记录表
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_customer_sure_class_log")
public class CustomerSureClassLog extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long customerSureClassId;
    /**
     * 顾客id
     */
    private Long customerId;
    /**
     * 顾客姓名
     */
    @TableField(exist = false)
    private String customerName;
    /**
     * 合同id
     */
    private Long contractId;
    /**
     * 合同名称
     */
    @TableField(exist = false)
    private String contractName;
    /**
     *
     */
    private Long customerPlanDayId;
    /**
     * 销课时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sureClassTime;
    /**
     * 销课时间
     */
    @TableField(exist = false)
    private String sureClassTimeStr;
    /**
     * 操作者姓名
     */
    private String operationName;
    /**
     * 从哪里销课
     */
    private String client;
    /**
     * 上课教练id
     */
    private Long coachId;
    /**
     * 教练姓名
     */
    @TableField(exist = false)
    private String coachName;
}
