package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/23 7:09 PM
 */
@Data
public class Outline {

  /**
   * body_mass_index-身体质量指数
   */
  private Double bmi;


  /**
   * 肌肉调节量(kg)【注：其对应体成分字段muscle_control】
   */
  private Double smmControl;

  /**
   * 脂肪调节量(kg)【注：其对应体成分字段fat_control】
   */
  private Double bfmControl;

  /**
   * skeletal_muscle_mass
   * <p>
   * 骨骼肌(kg)
   */
  private Double smm;

  /**
   * percent_body_fat-
   * <p>
   * 体脂百分比(%)
   */
  private Double pbf;

  /**
   * 肌肉量(kg)
   */
  private Double muscle;

  /**
   * 脂肪量(kg)【注：其对应体成分字段fat】
   */
  private Double bfm;

  /**
   * 内脏脂肪(kg)
   */
  private Double vfi;

  /**
   * 基础代谢(kcal)
   */
  private Double bmr;

  /**
   * 总能量消耗(kcal)【注：其对应体成分字段tee】
   */
  private Double cbc;


  /**
   * 身体年龄(岁)
   */
  private Integer bodyAge;


}
