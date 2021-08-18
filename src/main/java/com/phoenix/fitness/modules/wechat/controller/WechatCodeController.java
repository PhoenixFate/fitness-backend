package com.phoenix.fitness.modules.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.phoenix.fitness.modules.fitness.vo.DecryMobileForm;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phoenix.fitness.common.utils.R;
import com.phoenix.fitness.common.utils.WechatDecryptDataUtil;

import javax.validation.Valid;
import java.net.URI;

/**
 * 微信小程序
 * 解码code
 * 解码手机号码
 * @author phoenix
 */
@RestController
@RequestMapping("wechat/code")
public class WechatCodeController {

    @Value("${fitness.wechat.appId}")
    private String appId;
    @Value("${fitness.wechat.appSecret}")
    private String appSecret;

    @GetMapping()
    private ResponseEntity<R> decryptCode(String code) {
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+appId+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            URI uri = builder.build();
            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 解析json
        JSONObject jsonObject = (JSONObject) JSONObject.parse(resultString);
        String session_key = jsonObject.get("session_key") + "";
        String openid = jsonObject.get("openid") + "";
        System.out.println("session_key=="+ session_key);
        System.out.println("openid=="+ openid);
        return ResponseEntity.ok(new R(jsonObject));
    }

    @PostMapping("/mobile")
    private ResponseEntity<R> decryptMobile(@RequestBody @Valid DecryMobileForm decryMobileForm){
        String data = WechatDecryptDataUtil.decryptData(decryMobileForm.getEncryptedData(), decryMobileForm.getSessionKey(), decryMobileForm.getIv());
        JSONObject jsonObject = (JSONObject) JSONObject.parse(data);
        return ResponseEntity.ok(new R(jsonObject));
    }


}

