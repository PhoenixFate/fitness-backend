package com.phoenix.fitness.modules.fitness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.dto.TotalBillDto;
import com.phoenix.fitness.modules.admin.form.BillSearchForm;
import com.phoenix.fitness.modules.admin.form.ContractSearchForm;
import com.phoenix.fitness.modules.admin.form.FinishTimesForm;
import com.phoenix.fitness.modules.admin.form.RefundContractForm;
import com.phoenix.fitness.modules.fitness.entity.CustomerContractEntity;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;

/**
 * 合同service
 *
 * @author phoenix
 * @email sm516116978@outlook.com
 * @date 2021-06-18 11:28:53
 */
public interface CustomerContractService extends IService<CustomerContractEntity> {

    PageUtils queryPage(ContractSearchForm contractSearchForm);

    PageUtils queryBillPage(BillSearchForm billSearchForm) throws ParseException;

    TotalBillDto getTotal(BillSearchForm contractSearchForm) throws ParseException;

    void active(CustomerContractEntity customerContractEntity) throws ParseException;

    List<CustomerContractEntity> getOneCustomerContracts(Long customerId);

    void saveContract(CustomerContractEntity customerContractEntity) throws ParseException;

    void deleteContract(Long customerContractId) throws ParseException;

    void finishTimes(FinishTimesForm finishTimesForm);

    void exportToExcel(BillSearchForm billSearchForm, HttpServletResponse response) throws ParseException;

    void refundContract(RefundContractForm refundContractForm);
}

