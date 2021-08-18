package com.phoenix.fitness.modules.fitness.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信支付的配置bean
 */
@Data
@Component
@ConfigurationProperties(prefix = "fitness.wechatpay")
public class WxPayV3Bean {
    /**
     * 服务商/直连商户平台 公众号appid
     */
    private String appId;
    /**
     * 服务商id/商户id
     */
    private String mchId;
    /**
     * 秘钥
     */
    private String keyPath;
    /**
     * CA证书 格式.pem
     */
    private String certPath;
    /**
     * CA证书 格式.p12
     */
    private String certP12Path;
    /**
     * 平台证书路径
     */
    private String platformCertPath;
    /**
     * 自定义api密钥
     */
    private String apiKey;
    /**
     * 通知回调路径
     */
    private String notifyUrl;

}