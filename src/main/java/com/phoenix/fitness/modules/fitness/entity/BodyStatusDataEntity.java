package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 顾客体态数据
 * 1个顾客 ： n个体态数据
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 * @Deprecated in favor of posture
 */
@Data
@TableName("tb_body_status_data")
@Deprecated
public class BodyStatusDataEntity extends BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * id
   */
  @TableId
  private Long bodyStatusId;
  /**
   * 体态正面照
   */
  private String frontImage;
  /**
   * 体态侧面照
   */
  private String sideImage;
  /**
   * 体态背面照
   */
  private String backImage;
  /**
   * 教练提出的建议
   */
  private String advice;
  /**
   * 体态总揽
   */
  private Integer bodyCommon;
  /**
   * 脊柱前凸
   */
  private Integer spineLordosis;
  /**
   * 圆肩
   */
  private Integer roundShoulder;
  /**
   * 颈椎前倾
   */
  private Integer cervicalLordosis;
  /**
   * 高低腰
   */
  private Integer highOrLowWaist;
  /**
   * 高低肩
   */
  private Integer highOrLowShoulder;
  /**
   * 头部倾斜
   */
  private Integer headTilt;
  /**
   * 关联的顾客
   */
  private Long customerId;
  /**
   * 是否是最新的一条数据
   */
  private Integer isLatest;
  /**
   * 记录的日期
   */
  private Date addTime;
}
