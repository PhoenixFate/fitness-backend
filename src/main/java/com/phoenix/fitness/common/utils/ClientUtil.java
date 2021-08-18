package com.phoenix.fitness.common.utils;

import com.phoenix.fitness.common.constant.ClientEnum;
import org.springframework.util.StringUtils;

public class ClientUtil {

    public static String getClientChinese(String client) {
        if (StringUtils.isEmpty(client)) {
            return "";
        } else {
            if (ClientEnum.ADMIN.getClientName().equals(client)) {
                return "后台管理";
            }
            if (ClientEnum.MINI_PROGRAM.getClientName().equals(client)) {
                return "小程序";
            }
            if (ClientEnum.PAD.getClientName().equals(client)) {
                return "平板";
            }
            return "未知";
        }
    }

}
