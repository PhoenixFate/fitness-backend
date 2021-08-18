package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author neil created at 2021/6/23 6:54 PM
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportQuery {

  private String phone;

  private String deviceSn;

  private String attachmentId;

  private String attachmentRom;

  private Integer page;
}
