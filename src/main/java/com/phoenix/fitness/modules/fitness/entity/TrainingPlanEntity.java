package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 训练计划模板
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:56
 */
@Data
@TableName("tb_training_plan")
public class TrainingPlanEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long trainingPlanId;
    /**
     * 训练计划模板名称
     */
    @NotBlank(message = "训练计划模板名称不能为空")
    @Size(max = 40, message = "训练计划模板名称长度1-40")
    private String trainingPlanName;
    /**
     * 训练计划介绍
     */
    @Size(max = 512, message = "训练计划介绍长度1-512")
    private String trainingPlanDescription;
    /**
     * 训练计划封面照片
     */
    @Size(max = 512, message = "训练计划封面照片长度1-512")
    private String trainingPlanCoverImage;
    /**
     * 训练目标id
     */
    @NotNull(message = "训练目标不能为空")
    private Long goalId;
    /**
     * 训练目标名称
     */
    @TableField(exist = false)
    private String goalName;
    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    private String price;
    /**
     * 优惠价格
     */
    private String favorablePrice;
    /**
     * 训练计划类型
     * 体验课
     * 具体多少节课的训练计划
     * 包月训练计划
     */
    private String trainingType;
    /**
     * 总课程数
     */
    private Integer totalClass;

}
