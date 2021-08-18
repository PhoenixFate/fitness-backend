package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 身体姿态检查
 * 来自于机器
 *
 * @author neil created at 2021/7/1 1:37 PM
 */
@Data
@TableName("physique_exam_posture")
public class PhysiqueExamPostureEntity extends BaseEntity {

  @TableId
  private Integer id;

  private Integer customerId;

  /**
   * 总体得分
   */
  private Double score;

  /**
   * 风险评估
   */
  private String evaluation;

  /**
   * 高低肩（成人+儿童）
   */
  private Integer unevenShouldersId;

  /**
   * 0/X型腿（成人+儿童）
   */
  private Integer legTypeId;

  /**
   * 颈椎前沿（成人+儿童）
   */
  private Integer neckForwardId;

  /**
   * 圆肩驼背（成人+儿童）
   */
  private Integer roundShoulderedHunchbackId;

  /**
   * 骨盆倾斜（成人）
   */
  private Integer pelvicObliquityId;

  /**
   * 盆骨侧倾（成人）
   */
  private Integer pelvisFlankId;

  /**
   * 膝超伸（成人）
   */
  private Integer kneeOverextensionId;

  /**
   * 颈椎侧弯（成人+儿童）
   */
  private Integer cervicalScoliosisId;

  /**
   * 脊柱侧弯（成人+儿童）
   */
  private Integer scoliosisId;

  /**
   * 原始图
   */
  private Integer sourceImageId;

  /**
   * 脊柱图
   */
  private Integer spineImageId;


  private Date examTime;

}
