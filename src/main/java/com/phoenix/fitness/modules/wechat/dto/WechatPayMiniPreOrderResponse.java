package com.phoenix.fitness.modules.wechat.dto;

import lombok.Data;

@Data
public class WechatPayMiniPreOrderResponse {

    private String appId;

    private String timeStamp;

    private String nonceStr;

    private String prepay_id;

    private String singType;

    private String paySign;
}
