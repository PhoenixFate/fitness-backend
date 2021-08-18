package com.phoenix.fitness.modules.admin.form;

import lombok.Data;

@Data
public class BaseSearchForm {
    //分页参数
    private Long page = 1L;
    private Long limit = 10L;
}
