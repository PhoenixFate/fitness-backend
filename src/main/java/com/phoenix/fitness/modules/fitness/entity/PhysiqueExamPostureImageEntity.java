package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 身体姿态检查当中涉及的图片
 * 来自于机器
 *
 * @author neil created at 2021/7/1 1:37 PM
 */
@Data
@TableName("physique_exam_posture_image")
public class PhysiqueExamPostureImageEntity {

  @TableId
  private Integer id;

  /**
   * parent id
   */
  private Integer postureExamId;

  private String front;

  private String back;

  private String leftImage;


  public void setLeft(String left) {
    this.leftImage = left;
  }

  public String getLeft() {
    return leftImage;
  }

}
