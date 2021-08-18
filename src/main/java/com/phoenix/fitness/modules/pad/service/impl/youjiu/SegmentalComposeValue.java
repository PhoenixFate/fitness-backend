package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * r/l-> right/left;
 * a/l -> arm/leg;
 * @author neil created at 2021/6/24 9:39 AM
 */
@Data
public class SegmentalComposeValue {

  private ComposeValue ra;

  private ComposeValue la;

  /**
   * 躯干-trunk
   */
  private ComposeValue tr;

  private ComposeValue rl;

  private ComposeValue ll;
}
