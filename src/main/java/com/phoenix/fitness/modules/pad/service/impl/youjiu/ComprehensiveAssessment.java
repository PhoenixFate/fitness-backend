package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

import java.util.Date;

/**
 * 综合评估[报告关联信息]
 *
 * @author neil created at 2021/6/24 9:21 AM
 */
@Data
public class ComprehensiveAssessment {

  /**
   * 综合评估 ID
   */
  private Integer caId;

  /**
   * 报告内容标识，
   */
  private Integer inclusion;

  /**
   * 类型（当前仅3t类型）
   */
  private String type;

  /**
   * 姿态筛查报告ID
   */
  private Integer measPostureId;

  /**
   * 姿态筛查报告本地ID
   */
  private String measPostureLocalId;

  /**
   * 姿态筛查测试时间
   */
  private Date measPostureTime;

  /**
   * 体成分报告ID
   */
  private Integer measCompositionId;

  /**
   * 体成分报告本地ID
   */
  private String measCompositionLocalId;

  /**
   * 体成分报告测试时间
   */
  private Date measCompositionTime;
}
