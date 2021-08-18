package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author neil created at 2021/6/29 10:12 AM
 */
@TableName("physique_exam_syncing_log")
@Data
public class PhysiqueExamSyncingLog extends BaseEntity {

  @TableId
  private Long id;

  private Date receivedTime;

  private String measureId;

  private String content;

  private Long customerId;

  private Boolean processed;
}
