package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

import java.util.Date;

/**
 * @author neil created at 2021/6/23 6:58 PM
 */
@Data
public class Measurement {

  /**
   * 测试 ID
   */
  private String id;

  /**
   * 综合评估 ID
   */
  private String caId;

  /**
   * 测试设备 SN 码
   */
  private String deviceSn;

  /**
   * 报告归属产品名称
   */
  private String proName;

  /**
   * 测试时设备版本号
   */
  private String versionNo;

  /**
   * 三方登录用户UID
   */
  private String thirdUid;

  /**
   * 三方登录用户登录用户扩展信息
   */
  private String extendData;

  /**
   * 测试手机号
   */
  private String phone;

  /**
   * 测试输入身高(cm)
   */
  private Double height;

  /**
   * 测试输入体重(kg)
   */
  private Double weight;

  /**
   * 测试输入性别：
   *
   * @1：男
   * @2：女
   */
  private Integer gender;

  /**
   * 测试输入年龄
   */
  private Double age;

  /**
   * 体成分风险等级@1:健康@2:低风险@3:中风险@4:高风险
   */
  private String score;

  /**
   * 测试结果概要
   */
  private Outline outline;

  /**
   * 登录方式，
   * 值	类型
   * 1	匿名
   * 2	微信登录
   * 3	手机号登录
   * 4	第三方APP登录
   * 5	第三方手环登陆
   * 6	USB
   * 7	Push登录
   * 8	扫码器登录
   * 9	身份证登录
   * 11	匿名手机号登录
   * 12	绑定手机号登录
   * 13	匿名邮箱登录
   * 14	绑定邮箱登录
   * 51	三方人脸登录
   * 52	三方扫码登录（设备人脸识别摄像头扫码）
   * 53	三方扫码登录（设备生成二维码被扫）
   * 54	三方输入ID登录
   * 55	三方NFC识别登录
   */
  private Integer loginType;

  /**
   * 测试时间
   */
  private Date startTime;

  /**
   * 报告内容标识
   * 平衡性	型美维度	姿态筛查	姿态+维度	体成分	值	产品
   * 体成分	        0	0	0	0	1	            1	pro、xone+
   * 姿态+维度	      0	0	0	1	0	            2	xone+
   * 老版合成报告	    0	0	0	1	1	            3	xone+
   * 姿态筛查	        0	0	1	0	0	            4	pro
   * 姿态筛查+体成分	0	0	1	0	1	            5	pro
   * 型美维度	        0	1	0	0	0	            8	pro
   * 平衡性	        1	0	0	0	0	            16	pro
   */
  private Integer inclusion;
}
