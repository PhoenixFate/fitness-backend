package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 身体姿态检查的某一项,
 * 来自于机器
 * 由于结构单一,抽象放置于同一个表中
 *
 * @author neil created at 2021/7/1 1:37 PM
 */
@Data
@TableName("physique_exam_posture_item")
public class PhysiqueExamPostureItemEntity {

  @TableId
  private Integer id;

  /**
   * parent id
   */
  private Integer postureExamId;

  /**
   * 名称
   */
  private String name;

  /**
   * example: [0,5,15,30]
   * 得分间隔（数组）
   */
  @TableField("value_interval")
  private String interval;

  /**
   * example: ["正常","轻微","中度","重度"]
   * 等级类别（数组）
   */
  private String grades;

  /**
   * 值
   */
  private String value;

  /**
   * 单位
   */
  private String unit;

  /**
   * 等级
   */
  private String grade;

  /**
   * 描述
   */
  @TableField("describe_val")
  private String describe;

  /**
   * 标注图URL
   */
  private String markFigure;

  /**
   * 标注图聚焦图URL
   */
  private String markFigureFocus;


}
