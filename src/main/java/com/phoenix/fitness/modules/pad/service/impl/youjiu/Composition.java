package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * 体成分[报告数据]
 *
 * @author neil created at 2021/6/24 9:21 AM
 */
@Data
public class Composition {

  /**
   * 体重
   */
  private ComposeValue weight;

  /**
   * 脂肪量
   */
  private ComposeValue fat;

  /**
   * 去脂体重-fat_free_mass
   */
  private ComposeValue ffm;

  /**
   * 无机盐
   */
  private ComposeValue mineral;

  /**
   * 肌肉量
   */
  private ComposeValue muscle;

  /**
   * 蛋白质
   */
  private ComposeValue protein;

  /**
   * 身体总水分-total_body_water
   */
  private ComposeValue tbw;

  /**
   * 节段肌肉量
   */
  private SegmentalComposeValue segmentalMuscle;

  /**
   * 节段脂肪量
   */
  private SegmentalComposeValue segmentalFat;

  /**
   * 腰臀脂肪比
   */
  private ComposeValue whfr;

  /**
   * 体脂率-percent_body_fat
   */
  private ComposeValue pbf;

  /**
   * 骨骼肌-skeletal_muscle_mass
   */
  private ComposeValue smm;

  /**
   * 健壮指数(评估等级商家自己定义)
   */
  private ComposeValue strongIndex;

  /**
   * 基础代谢-basal_metabolic_rate
   */
  private ComposeValue bmr;

  /**
   * 总能量消耗-total_energy_consumption
   */
  private ComposeValue tee;

  /**
   * 身体质量指数-body_mass_index
   */
  private ComposeValue bmi;

  /**
   * following index should be ignore or not supported
   */
  private String ecwStage;

  private String ecfStage;

  private String insideWater;

  private String outsideWater;

  private String bodyAgeOffset;

  private ComposeValue figure;

  private String evaluation;

  private ComposeValue score;

  private ComposeValue bodyScore;

  private ComposeValue calcium;

  private ComposeValue muscleControl;

  private ComposeValue fatControl;


}
