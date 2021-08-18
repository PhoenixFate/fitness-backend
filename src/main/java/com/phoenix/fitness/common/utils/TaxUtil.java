package com.phoenix.fitness.common.utils;

import com.phoenix.fitness.common.constant.PayWayEnum;

import java.text.ParseException;


public class TaxUtil {

    public static double getTax(String payType) {
        //计算税后支付金额
        double tax = 0.00;
        //根据支付方式计算税率
        if (PayWayEnum.ALIPAY.getPayWayName().equals(payType)
                || PayWayEnum.WECHAT.getPayWayName().equals(payType)
        ) {
            //支付宝、微信税率0.006
            tax = 0.006;
        } else if (PayWayEnum.UNION_PAY.getPayWayName().equals(payType)) {
            //银联，税率0.0035
            tax = 0.0035;
        } else if (PayWayEnum.PUBLIC_COMMENT.getPayWayName().equals(payType)) {
            //大众点评 7%
            tax = 0.07;
        }
        return tax;
    }


    public static void main(String[] args) throws ParseException {
        System.out.println(TaxUtil.getTax("abx"));
        System.out.println(TaxUtil.getTax(null));
        System.out.println(TaxUtil.getTax("ALIPAY"));
    }
}

