package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 10:20 AM
 */
@Data
public class PostureDetailValue {

  /**
   * 名称
   */
  private String name;

  /**
   * example: [0,5,15,30]
   * 得分间隔（数组）
   */
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
