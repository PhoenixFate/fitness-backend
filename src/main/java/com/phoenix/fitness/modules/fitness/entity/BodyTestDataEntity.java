package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 体测数据
 * 1个顾客 ： n个体测数据
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Data
@TableName("tb_body_test_data")
public class BodyTestDataEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long bodyTestId;
    /**
     * 顾客id
     */
    @NotNull(message = "顾客id不能为空")
    private Long customerId;
    /**
     * 身高
     */
    private Double height;
    /**
     * 体重
     */
    private Double weight;
    /**
     * bmi
     */
    private Double bmi;
    /**
     * bmr
     */
    private Double bmr;
    /**
     * 肩宽
     */
    private Double shoulder;
    /**
     * 胸围
     */
    private Double bust;
    /**
     * 腰围
     */
    private Double waistLine;
    /**
     * 臀围
     */
    private Double hipLine;
    /**
     * 血压
     */
    private Double bloodPressure;
    /**
     * 体脂
     */
    private Double bodyFat;
    /**
     * 最大心率
     */
    private Double maxHeartRate;
    /**
     * 静态心率
     */
    private Double staticHeartRate;
    /**
     * 右上臂
     */
    private Double rightUpperArm;
    /**
     * 左上臂
     */
    private Double leftUpperArm;
    /**
     * 右大腿
     */
    private Double rightThigh;
    /**
     * 左大腿
     */
    private Double leftThigh;
    /**
     * 是否是最新的一条数据
     */
    private Integer isLatest;
    /**
     * 体测数据的日期
     */
    private Date addTime;
}
