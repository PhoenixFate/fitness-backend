package com.phoenix.fitness.common.utils;

import com.phoenix.fitness.common.constant.ContractStatusEnum;
import org.springframework.util.StringUtils;

public class ContractStatusUtil {

    //将文件转换成Byte数组
    public static String getContractStatusChinese(String contractStatus) {
        if (StringUtils.isEmpty(contractStatus)) {
            return "";
        } else {
            if (ContractStatusEnum.PREPAYMENT.getContractStatusName().equals(contractStatus)) {
                return "预收费";
            }
            if (ContractStatusEnum.NO_START.getContractStatusName().equals(contractStatus)) {
                return "未开始";
            }
            if (ContractStatusEnum.IN_PROGRESS.getContractStatusName().equals(contractStatus)) {
                return "进行中";
            }
            if (ContractStatusEnum.FINISHED.getContractStatusName().equals(contractStatus)) {
                return "已结束";
            }
            if (ContractStatusEnum.EXPIRED.getContractStatusName().equals(contractStatus)) {
                return "未使用完，但过期";
            }
            if (ContractStatusEnum.DELETE_MANUALLY.getContractStatusName().equals(contractStatus)) {
                return "手动删除";
            }
            return "未知";
        }
    }

}
