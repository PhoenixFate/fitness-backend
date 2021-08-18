package com.phoenix.fitness.modules.fitness.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.phoenix.fitness.modules.fitness.entity.CoachEntity;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import lombok.Data;

import java.util.Date;

/**
 * Entity log the timestamp of the classes
 *
 * @author neil created at 2021/6/18 11:56 AM
 */
@Data
@TableName("customer_vip_class_checkin")
public class CustomerVipClassCheckinDto {


  private CustomerEntity customer;

  private Date checkinTime;

  private Long classId;

  private CoachEntity coach;

  private Long gymId;

}
