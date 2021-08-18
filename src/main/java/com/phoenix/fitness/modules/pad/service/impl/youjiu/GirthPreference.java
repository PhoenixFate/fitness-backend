package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 10:43 AM
 */
@Data
public class GirthPreference {

  /**
   * 胸围(cm)
   */
  private Double chest;

  /**
   * 臀围(cm)
   */
  private Double hip;

  /**
   * 肩宽(cm)
   */
  private Double shoulder;

  /**
   * 腿长(cm)
   */
  private Double thigh;

  /**
   * 腰围(cm)
   */
  private Double waist;
}
