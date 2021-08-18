package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Entity log the timestamp of the classes
 *
 * @author neil created at 2021/6/18 11:56 AM
 */
@Data
@TableName("customer_vip_class_checkin")
public class CustomerVipClassCheckinEntity extends BaseEntity {

  @TableId
  public Long id;

  public Long customerId;

  public Date checkinTime;

  /**
   * customer_plan_day id
   */
  public Long classId;

  public Long trainerId;

}
