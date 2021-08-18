package com.phoenix.fitness.modules.fitness.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class BaseEntity {
    /**
     * 健身房id
     */
//    @JsonIgnore
    private Long gymId;
    /**
     * 合作伙伴id
     */
//    @JsonIgnore
    private Long partnerId;
    /**
     * 创建时间
     * 默认当前时间
     */
    // @JsonIgnore
    private Date createTime;
    /**
     * 修改时间
     * 默认当前时间
     */
    @JsonIgnore
    private Date updateTime;
    /**
     * 是否删除
     * 0未删除
     * 1已删除
     * 默认0
     */
    @JsonIgnore
    private Integer deleteFlag;
}
