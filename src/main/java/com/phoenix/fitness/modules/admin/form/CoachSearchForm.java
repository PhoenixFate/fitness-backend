package com.phoenix.fitness.modules.admin.form;

import lombok.Data;

/**
 * 教练搜索表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class CoachSearchForm extends BaseSearchForm {
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 教练姓名
     */
    private String coachName;

    private Integer gymId;
}
