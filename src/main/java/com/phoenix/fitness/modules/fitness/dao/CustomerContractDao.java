package com.phoenix.fitness.modules.fitness.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.phoenix.fitness.modules.fitness.dto.CustomerContractWithCustomerDto;
import com.phoenix.fitness.modules.fitness.dto.CustomerContractWithLogDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.phoenix.fitness.modules.admin.form.BillSearchForm;
import com.phoenix.fitness.modules.admin.form.ContractSearchForm;
import com.phoenix.fitness.modules.admin.form.CustomerSearchForm;

import java.util.List;

/**
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2020-10-24 11:33:55
 */
@Mapper
public interface CustomerContractDao extends BaseMapper<CustomerContractEntity> {

    List<CustomerContractEntity> selectCustomerContracts(Long customerId);

    List<CustomerContractEntity> selectCustomerContractsSpecial(Long customerId);

    IPage<CustomerContractWithCustomerDto> selectContractListWithCustomer(IPage<CustomerContractWithCustomerDto> pageParams, @Param("searchForm") ContractSearchForm searchForm);

    IPage<CustomerContractWithLogDto> selectContractListWithLog(IPage<CustomerContractWithLogDto> pageParams, @Param("searchForm") BillSearchForm billSearchForm);

    List<CustomerContractWithLogDto> selectContractListWithLogNoPage(@Param("searchForm") BillSearchForm billSearchForm);

    IPage<CustomerContractWithLogDto> selectCustomerMarketTrainingPage(IPage<CustomerContractWithLogDto> pageParams,
                                                           @Param("searchForm") CustomerSearchForm searchForm);

}
