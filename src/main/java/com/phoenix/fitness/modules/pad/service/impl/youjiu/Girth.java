package com.phoenix.fitness.modules.pad.service.impl.youjiu;

import lombok.Data;

/**
 * @author neil created at 2021/6/24 9:22 AM
 */
@Data
public class Girth {

  /**
   * 身材类型
   * 男性@100:匀称型身材@101:倒梯形型身材@102:倒三角身材@103:矩形身材@104:椭圆形身材@105:三角形身材
   * 女性@0:匀称型身材@1:沙漏型身材@2:草莓型身材@3:香蕉型身材@4:苹果型身材@5:梨子型身材
   */
  private Integer bodyType;

  /**
   * 头全高(cm)
   */
  private Double headHeight;

  /**
   * 肩宽(cm)
   */
  private Double shoulder;

  /**
   * 胸围(cm)
   */
  private Double chest;

  /**
   * 腰围(cm)
   */
  private Double waist;

  /**
   * 臀围(cm)
   * 亚洲男性理想腰臀比0.85～0.95
   * 亚洲女性理想腰臀比0.67～0.80
   */
  private Double hip;

  /**
   * 左大腿
   */
  private Double lt;

  /**
   * 右大腿
   */
  private Double rt;

  /**
   * 个人建议/标准参考值
   */
  private GirthPreference prv;

  /**
   * 头身比
   * 亚洲男性平均为7.18头身，亚洲女性平均为6.95头身
   * 欧美男性平均为7.57头身，欧美女性平均为7.49头身
   * 头身比=身高/头全高
   */
  private Double hbr;

  /**
   * 肩臀比
   * 亚洲男性理想肩臀比0.85～0.95
   * 肩臀比=肩宽/臀围
   */
  private Double shr;

  /**
   * 腿身比
   * 亚洲男性平均为45.70，亚洲女性平均为44.90
   * 欧美男性平均为47.68，欧美女性平均为47.34
   * 腿身比=腿长/身高 * 100
   */
  private Double lbr;

  /**
   * 腰臀比
   * 亚洲女性理想腰臀比0.67～0.80
   * 腰臀比=腰围/臀围
   */
  private Double whr;

  /**
   * 标注图
   */
  private String skeletonPointImageUris;

  /**
   * 原始图
   */
  private PostureSide sourceImage;

  /**
   * 脊柱图
   */
  private PostureSide spineImage;
}
