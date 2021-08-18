package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 10:24 AM
 */
@Data
public class PostureDescribe {

  /**
   * 症状
   */
  private String name;

  /**
   * 引起的原因
   */
  private String causes;

  /**
   * 导致哪些不适
   */
  private String risks;


}
