package com.phoenix.fitness.common.utils;

import com.phoenix.fitness.common.constant.PayWayEnum;
import org.springframework.util.StringUtils;

public class PayTypeUtil {

    //将文件转换成Byte数组
    public static String getPayTypeChinese(String payType) {
        if (StringUtils.isEmpty(payType)) {
            return "";
        } else {
            if (PayWayEnum.ALIPAY.getPayWayName().equals(payType)) {
                return "支付宝";
            }
            if (PayWayEnum.WECHAT.getPayWayName().equals(payType)) {
                return "微信";
            }
            if (PayWayEnum.PUBLIC_COMMENT.getPayWayName().equals(payType)) {
                return "大众点评";
            }
            if (PayWayEnum.UNION_PAY.getPayWayName().equals(payType)) {
                return "银联";
            }
            if (PayWayEnum.CASH.getPayWayName().equals(payType)) {
                return "现金";
            }
            return "未知";
        }
    }

}
