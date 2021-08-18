package com.phoenix.fitness.modules.fitness.dto;

import lombok.Data;
import com.phoenix.fitness.modules.pad.service.impl.youjiu.Posture;

import java.util.Date;

/**
 * @author neil created at 2021/7/2 10:34 AM
 */
@Data
public class PostureRecord extends Posture {

  private Date examTime;

  private Integer customerId;

  private Integer gymId;
}
