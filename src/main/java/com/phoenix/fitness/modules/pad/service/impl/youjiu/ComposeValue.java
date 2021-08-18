package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 9:28 AM
 */
@Data
public class ComposeValue {

  /**
   * 测试值(kg)
   */
  private Double value;

  /**
   * 评估等级@1偏低:@2正常:@3:偏高
   */
  private Integer grade;

  /**
   * 个人参考值
   * example: [57.2,69.9]
   */
  private String prv;
}

