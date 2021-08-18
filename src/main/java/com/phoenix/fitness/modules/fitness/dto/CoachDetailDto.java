package com.phoenix.fitness.modules.fitness.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.phoenix.fitness.modules.fitness.entity.GymEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class CoachDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long coachId;
    /**
     * 教练姓名
     */
    private String coachName;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 账户名
     */
    private String username;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 身份证
     */
    private String identityCard;
    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 单课价格
     */
    private String perClassPrice;

    private Long userId;

    private List<CoachDetailWithTargetDto> coachTargetDetailList;

    private List<GymEntity> gyms;
}
