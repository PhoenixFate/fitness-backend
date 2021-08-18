package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 9:22 AM
 */
@Data
public class Balance {

  /**
   * example:[[1,91,85],[2,98,75],[3,41,105]]
   * 稳定性数组
   * 构成说明：[时间点（毫秒），核心机得分，膝关节得分 ]
   */
  private String stability;

  /**
   * example:[[0,0,6000],[1,6000,14000],[2,14000,17000]]
   * 稳定性阶段数组（单位：毫秒）
   * 构成说明：[阶段类型（0稳定阶段，1维稳阶段，2失衡阶段）,开始时间（毫秒），截止时间（毫秒） ]
   */
  private String stabilityStage;

  /**
   * 动作标志性得分（满分50分）
   */
  private Double actionStandard;


  /**
   * 测试预定时长（秒）
   */
  private Double predeterTime;

  /**
   * 结束时间（毫秒）
   */
  private Double endReasonTime;

  /**
   * 结束原因类型:
   *
   * @-1:未知原因
   * @0:完成完整测试
   * @1:眼睛睁开
   * @2:身体倾倒
   * @3:支撑脚移动
   * @4:动作无效
   * @5:手偏移
   * @6:腿部动作不规范
   * @7:手部动作不规范
   */
  private Integer endReasonType;

  /**
   * 综合评分
   * [170~200]分:优秀
   * [140~169]分:良好
   * [110~139]分:一般
   * [0~109]分:待加强
   * 计算方式：综合评分 =动作标准性得分 +核心稳定性得分 +下肢稳定性得分 +坚持时长（秒）*2
   */
  private Double totalScore;

  /**
   * 核心稳定性得分
   * [0,26)分:你的核心稳定性有待加强，建议在日常训练中加强腰部、腹部和脊柱的伸展及训练。
   * [26,41)分:你的核心稳定性有待加强，建议在日常训练中加强腰部、腹部和脊柱的伸展及训练。
   * [41,50]分:你的核心稳定性较好，建议在日常训练中保持腰部、腹部和脊柱的伸展及训练。
   */
  private Double coreStability;

  /**
   * 下肢稳定性得分
   * [0,26)分:你的膝关节无法在抬起一定高度时保持稳定，建议加强臀部外侧肌肉、下肢及脚踝周围的肌腱和韧带的力量，在训练中试着放松站立腿的膝盖，让站立腿的四头肌发力，以便配合臀部外侧肌肉帮助你保持平衡。
   * [26,41)分:你的膝关节不能完全在抬起一定高度时保持稳定，建议加强臀部外侧肌肉、下肢及脚踝周围的肌腱和韧带的力量，在训练中试着放松站立腿的膝盖，让站立腿的四头肌发力，以便配合臀部外侧肌肉帮助你保持平衡。
   * [41,50]分:你的膝关节能够在抬起一定高度时保持稳定，建议保持训练臀部外侧肌肉、下肢及脚踝周围的肌腱和韧带的力量，以进一步提升稳定性
   */
  private Double lowerLimbStability;

  /**
   * 晃动幅度图片 url （有效期 24h）
   */
  private String swagAmplitudePicture;

  /**
   * 阶段百分比:稳定阶段
   */
  private String balanceStagePercentage;

  /**
   * 阶段百分比:维稳阶段
   */
  private String fluctuationStagePercentage;

  /**
   * 阶段百分比:失衡阶段
   */
  private String imbalanceStagePercentage;
}
