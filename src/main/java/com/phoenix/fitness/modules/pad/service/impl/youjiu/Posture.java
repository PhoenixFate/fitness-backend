package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 9:22 AM
 */
@Data
public class Posture {

  private Double score;

  /**
   * 风险评估
   */
  private String evaluation;

  /**
   * 高低肩（成人+儿童）
   */
  private PostureDetailValue unevenShoulders;

  /**
   * 0/X型腿（成人+儿童）
   */
  private PostureDetailValue legType;

  /**
   * 颈椎前沿（成人+儿童）
   */
  private PostureDetailValue neckForward;

  /**
   * 圆肩驼背（成人+儿童）
   */
  private PostureDetailValue roundShoulderedHunchback;

  /**
   * 骨盆倾斜（成人）
   */
  private PostureDetailValue pelvicObliquity;

  /**
   * 盆骨侧倾（成人）
   */
  private PostureDetailValue pelvisFlank;

  /**
   * 膝超伸（成人）
   */
  private PostureDetailValue kneeOverextension;

  /**
   * 颈椎侧弯（成人+儿童）
   */
  private PostureDetailValue cervicalScoliosis;

  /**
   * 脊柱侧弯（成人+儿童）
   */
  private PostureDetailValue scoliosis;

  /**
   * 原始图
   */
  private PostureSide sourceImage;

  /**
   * 脊柱图
   */
  private PostureSide spineImage;

}
