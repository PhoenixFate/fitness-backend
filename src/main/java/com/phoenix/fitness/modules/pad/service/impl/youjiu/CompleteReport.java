package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 9:18 AM
 */
@Data
public class CompleteReport {

  /**
   * 体测[报告概要信息]
   */
  private Measurement measurement;

  /**
   * 综合评估[报告关联信息]
   */
  private ComprehensiveAssessment comprehensiveAssessment;

  /**
   * 体成分[报告数据]
   */
  private Composition composition;

  /**
   * 姿态筛查[报告数据]
   */
  private Posture posture;

  /**
   * 形美围度[报告数据]
   */
  private Girth girth;

  /**
   * 平衡性[报告数据]
   */
  private Balance balance;
}
