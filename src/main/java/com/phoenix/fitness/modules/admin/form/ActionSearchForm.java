package com.phoenix.fitness.modules.admin.form;

import lombok.Data;

/**
 * 动作过滤表单
 *
 * @author Mark sm516116978@outlook.com
 */
@Data
public class ActionSearchForm {
    /**
     * 动作名称
     */
    private String actionName;
    /**
     * 动作类型id
     */
    private Long actionTypeId;
    /**
     * 是否包含重量
     */
    private Integer containWeight;
    /**
     * 动作单位
     */
    private String unit;

}
