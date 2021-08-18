package com.phoenix.fitness.modules.fitness.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 访客表
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-07-20 16:03:42
 */
@Data
@TableName("tb_visitor")
public class VisitorEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Long visitorId;
    /**
     * 咨询时间
     */
    private Date visitTime;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 咨询项目
     */
    private String consultProject;
    /**
     * 跟进情况
     */
    private String followUp;
    /**
     * 所属时间段
     */
    private String period;
    /**
     * 添加日期
     */
    private Date addDate;
    /**
     * 添加的时间
     */
    private Date addTime;

}
