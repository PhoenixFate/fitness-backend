package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 会员卡
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Data
@TableName("tb_vip_card")
public class VipCardEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId
    private Long vipCardId;
    /**
     * 会员卡类型
     * 周卡：WEEK_CARD
     * 月卡：MONTH_CARD
     * 季卡：SEASON_CARD
     * 半年卡：HALF_YEAR_CARD
     * 三季度卡： THREE_SEASON_CARD
     * 年卡：YEAR_CARD
     */
    // @NotBlank(message = "会员卡类型不能为空")
    @Size(max = 40, message = "会员卡类型长度1-40")
    private String vipCardType;
    /**
     * 时间段卡 DURATION_CARD
     * 次卡    COUNT_CARD
     */
    private String vipCardBigType;
    /**
     * 会员卡名称
     */
    @NotBlank(message = "会员卡名称不能为空")
    @Size(max = 40, message = "会员卡名称长度1-512")
    private String vipCardName;
    /**
     * 会员卡图片
     */
    @Size(max = 512, message = "会员卡图片URL长度1-512")
    private String vipCardImage;
    /**
     * 原价
     */
    @NotBlank(message = "会员卡价格不能为空")
    @Pattern(regexp = "^[+]?(\\d+)|[+]?(\\d+\\.\\d+)$", message = "价格为大于0的数")
    private String price;
    /**
     * 优惠价
     */
    // @Pattern(regexp = "^[+]?(\\d+)|[+]?(\\d+\\.\\d+)$", message = "优惠价为大于0的数")
    private String favorablePrice;
    /**
     * 会员卡充值天数
     */
    // @NotNull(message = "会员卡充值天数不能为空")
    // @Min(value = 1, message = "会员卡充值天数为大于0的正整数")
    private Integer addVipDays;
    /**
     * 会员卡充值天数(女)
     */
    private Integer addVipDaysFemale;
    /**
     * 次卡可用次数
     */
    private Integer useCount;
    /**
     * 排序
     */
    private Integer sort;
}
