package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.form.CustomerSureClassSearchForm;
import com.phoenix.fitness.modules.fitness.entity.CustomerSureClassLog;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 销课记录
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-05-11 16:23:55
 */
public interface CustomerSureClassService extends IService<CustomerSureClassLog> {


    List<CustomerSureClassLog> getCustomerSureClassList(Long contractId);

    void deleteSureClass(Long customerSureClassId);

    PageUtils queryPage(CustomerSureClassSearchForm customerSureClassSearchForm) throws ParseException;

    void export(CustomerSureClassSearchForm customerSureClassSearchForm, HttpServletResponse response);
}

