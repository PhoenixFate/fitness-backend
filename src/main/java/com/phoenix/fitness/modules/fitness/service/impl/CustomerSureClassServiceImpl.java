package com.phoenix.fitness.modules.fitness.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.phoenix.fitness.common.utils.ClientUtil;
import com.phoenix.fitness.common.utils.DateUtils;
import com.phoenix.fitness.common.utils.ExcelResponseUtil;
import com.phoenix.fitness.common.utils.PageUtils;
import com.phoenix.fitness.modules.admin.form.CustomerSureClassSearchForm;
import com.phoenix.fitness.modules.fitness.dao.CustomerContractDao;
import com.phoenix.fitness.modules.fitness.dao.CustomerSureClassLogDao;
import com.phoenix.fitness.modules.fitness.entity.CustomerSureClassLog;
import com.phoenix.fitness.modules.fitness.service.CustomerSureClassService;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("customerSureClassService")
@AllArgsConstructor
public class CustomerSureClassServiceImpl extends ServiceImpl<CustomerSureClassLogDao, CustomerSureClassLog> implements CustomerSureClassService {

    private final CustomerSureClassLogDao customerSureClassLogDao;

    private final CustomerContractDao customerContractDao;

    @Override
    public List<CustomerSureClassLog> getCustomerSureClassList(Long contractId) {
        return customerSureClassLogDao.selectList(new QueryWrapper<CustomerSureClassLog>().eq("contract_id", contractId));
    }

    @Override
    public void deleteSureClass(Long customerSureClassId) {
        customerSureClassLogDao.deleteById(customerSureClassId);
    }

    @Override
    public PageUtils queryPage(CustomerSureClassSearchForm customerSureClassSearchForm) throws ParseException {
        if (customerSureClassSearchForm.getSureClassTimeEnd() != null) {
            customerSureClassSearchForm.setSureClassTimeEnd(DateUtils.getEndOfOneDay(customerSureClassSearchForm.getSureClassTimeEnd()));
        }
        Page<CustomerSureClassLog> pageParams = new Page<>(customerSureClassSearchForm.getPage(), customerSureClassSearchForm.getLimit());
        IPage<CustomerSureClassLog> page = customerSureClassLogDao.selectCustomerSureClassList(pageParams, customerSureClassSearchForm);
        return new PageUtils(page);
    }

    @Override
    public void export(CustomerSureClassSearchForm customerSureClassSearchForm, HttpServletResponse response) {
        List<CustomerSureClassLog> list = customerSureClassLogDao.selectCustomerSureClassListNoPage(customerSureClassSearchForm);
        for (CustomerSureClassLog customerSureClassLog : list) {
            customerSureClassLog.setClient(ClientUtil.getClientChinese(customerSureClassLog.getClient()));
            customerSureClassLog.setSureClassTimeStr(DateUtils.formatLong(customerSureClassLog.getSureClassTime()));
        }
        Map<String, Object> filedNames = new LinkedHashMap<String, Object>();
        String titleName = "";
        Class<?> objClass = null;
        Object obj = null;
        try {
            if (!CollectionUtils.isEmpty(list)) {
                // ????????????list????????????Dao???????????????????????????
                obj = list;
                // excel???????????????
                filedNames.put("customerName", "????????????");
                filedNames.put("contractName", "????????????");
                filedNames.put("coachName", "????????????");
                filedNames.put("sureClassTimeStr", "????????????");
                filedNames.put("operationName", "????????????");
                filedNames.put("client", "???????????????");
                titleName = "????????????";
                objClass = CustomerSureClassLog.class;
                Integer[] widths = new Integer[filedNames.size()];
                Arrays.fill(widths, 15);
                widths[1] = 26;
                widths[3] = 24;
                // ??????ExcelResponseUtil
                ExcelResponseUtil.exportToExcel(response, obj, filedNames, titleName, objClass, widths);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}