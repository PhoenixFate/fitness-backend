package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.entity.CustomerSureClassLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.phoenix.fitness.modules.admin.form.CustomerSureClassSearchForm;

import java.util.List;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:54
 */
@Mapper
public interface CustomerSureClassLogDao extends BaseMapper<CustomerSureClassLog> {

    IPage<CustomerSureClassLog> selectCustomerSureClassList(IPage<CustomerSureClassLog> pageParams, @Param("searchForm") CustomerSureClassSearchForm searchForm);

    List<CustomerSureClassLog> selectCustomerSureClassListNoPage(@Param("searchForm") CustomerSureClassSearchForm searchForm);

}
