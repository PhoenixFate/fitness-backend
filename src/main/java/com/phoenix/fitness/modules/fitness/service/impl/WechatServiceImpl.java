package com.phoenix.fitness.modules.fitness.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethod;
import com.ijpay.core.kit.AesUtil;
import com.ijpay.core.kit.PayKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.core.utils.DateTimeZoneUtil;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxApiType;
import com.ijpay.wxpay.enums.WxDomain;
import com.ijpay.wxpay.model.v3.Amount;
import com.ijpay.wxpay.model.v3.Payer;
import com.ijpay.wxpay.model.v3.UnifiedOrderModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.phoenix.fitness.common.constant.ExceptionEnum;
import com.phoenix.fitness.common.constant.OrderStatusEnum;
import com.phoenix.fitness.common.constant.PayWayEnum;
import com.phoenix.fitness.common.exception.FitnessException;
import com.phoenix.fitness.common.utils.QrCodeUtil;
import com.phoenix.fitness.modules.fitness.dao.CustomerDao;
import com.phoenix.fitness.modules.fitness.dao.OrderDao;
import com.phoenix.fitness.modules.fitness.dao.PayPreOrderLogDao;
import com.phoenix.fitness.modules.fitness.domain.WxPayV3Bean;
import com.phoenix.fitness.modules.fitness.dto.PreOderResultDto;
import com.phoenix.fitness.modules.fitness.entity.CustomerEntity;
import com.phoenix.fitness.modules.fitness.entity.OrderEntity;
import com.phoenix.fitness.modules.fitness.entity.PayPreOrderLogEntity;
import com.phoenix.fitness.modules.fitness.service.OrderService;
import com.phoenix.fitness.modules.fitness.service.WechatService;
import com.phoenix.fitness.modules.pad.form.OrderForm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WechatServiceImpl implements WechatService {

    /**
     * 生成的二维码存放路径
     */
    @Value("${fitness.pay.qrCodePath}")
    private String qrCodePath;

    private final OrderService orderService;

    private final OrderDao orderDao;

    private final WxPayV3Bean wxPayV3Bean;

    private String serialNo;
    
    private final PayPreOrderLogDao payPreOrderLogDao;

    private final CustomerDao customerDao;

    @Override
    public PreOderResultDto newPreOrder(OrderForm orderForm) throws Exception {
        OrderEntity order = orderService.preOrder(orderForm);
        //调用微信生成支付二维码
        log.info("开始调用支付宝生成支付二维码...");
        String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 60);//支付有效时间
        UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                .setAppid(wxPayV3Bean.getAppId())
                .setMchid(wxPayV3Bean.getMchId())
                .setDescription(order.getOrderDescription())
                .setOut_trade_no(order.getOrderNumber())
                .setTime_expire(timeExpire)
                .setAttach(order.getOrderDescription())
                .setNotify_url(wxPayV3Bean.getNotifyUrl())
                .setAmount(new Amount().setTotal(1));

        log.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));
        IJPayHttpResponse response = WxPayApi.v3(
                RequestMethod.POST,
                WxDomain.CHINA.toString(),
                WxApiType.NATIVE_PAY.toString(),
                wxPayV3Bean.getMchId(),
                getSerialNumber(),
                null,
                wxPayV3Bean.getKeyPath(),
                JSONUtil.toJsonStr(unifiedOrderModel)
        );
        log.info("统一下单响应 {}", response);
        // 根据证书序列号查询对应的证书来验证签名结果
        boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Bean.getPlatformCertPath());
        log.info("verifySignature: {}", verifySignature);
        JSONObject jsonObject = JSONObject.parseObject(response.getBody());
        //成功
        //修改订单状态
        order.setOrderStatus(OrderStatusEnum.UNPAID.getStatusName());
        orderDao.updateById(order);

        //预下单日志
        PayPreOrderLogEntity payPreOrderLog=new PayPreOrderLogEntity();
        payPreOrderLog.setCoachId(order.getCoachId());
        payPreOrderLog.setCustomerId(order.getCustomerId());
        payPreOrderLog.setRequestParams(JSONObject.toJSONString(JSONUtil.toJsonStr(unifiedOrderModel)));
        payPreOrderLog.setResponseResult(JSONObject.toJSONString(response));
        payPreOrderLog.setOrderNumber(order.getOrderNumber());
        payPreOrderLog.setPayType(PayWayEnum.WECHAT.getPayWayName());
        payPreOrderLogDao.insert(payPreOrderLog);
        
        
        
        //将返回的二维码存储至本地
        String path = qrCodePath + order.getOrderNumber() + "-WechatPay.jpg";
        String httpPath = "pad/order/qrCode/" + order.getOrderNumber() + "-WechatPay.jpg";
        File file = new File(path);
        if (file.getParentFile() != null && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        QrCodeUtil.createQrCode(new FileOutputStream(file), jsonObject.getString("code_url"));

        PreOderResultDto preOderResultDto = new PreOderResultDto();
        preOderResultDto.setOrderNumber(order.getOrderNumber());
        preOderResultDto.setQrCodeImageUrl(httpPath);
        return preOderResultDto;
    }

    @Override
    public void notify(JSONObject wechatResponse) {
        String amountString = wechatResponse.getString("amount");
        Amount amount = JSONObject.parseObject(amountString, Amount.class);
        String orderNumber = wechatResponse.getString("out_trade_no");
        OrderEntity order = orderDao.selectOne(new QueryWrapper<OrderEntity>().eq("order_number", orderNumber));
        order.setPayMoney(amount.getTotal() / 100 + "");
        order.setPayWay(PayWayEnum.WECHAT.getPayWayName());
        //防止重复通知
        if (order.getOrderStatus().equals(OrderStatusEnum.UNPAID.getStatusName())) {
            this.orderService.afterNotify(order);
        }
    }

    @Override
    public AlipayTradeQueryResponse queryOrder(String orderId) throws Exception {
        return null;
    }

    @Override
    public String getPlatCertList() throws Exception {
        IJPayHttpResponse response = WxPayApi.v3(
                RequestMethod.GET,
                WxDomain.CHINA.toString(),
                WxApiType.GET_CERTIFICATES.toString(),
                wxPayV3Bean.getMchId(),
                getSerialNumber(),
                null,
                wxPayV3Bean.getKeyPath(),
                ""
        );
        String timestamp = response.getHeader("Wechatpay-Timestamp");
        String nonceStr = response.getHeader("Wechatpay-Nonce");
        String serialNumber = response.getHeader("Wechatpay-Serial");
        String signature = response.getHeader("Wechatpay-Signature");

        String body = response.getBody();
        int status = response.getStatus();

        log.info("serialNumber: {}", serialNumber);
        log.info("status: {}", status);
        log.info("body: {}", body);
        int isOk = 200;
        if (status == isOk) {
            cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray dataArray = jsonObject.getJSONArray("data");
            // 默认认为只有一个平台证书
            cn.hutool.json.JSONObject encryptObject = dataArray.getJSONObject(0);
            cn.hutool.json.JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
            String associatedData = encryptCertificate.getStr("associated_data");
            String cipherText = encryptCertificate.getStr("ciphertext");
            String nonce = encryptCertificate.getStr("nonce");
            String serialNo = encryptObject.getStr("serial_no");
            final String platSerialNo = savePlatformCert(associatedData, nonce, cipherText, wxPayV3Bean.getPlatformCertPath());
            log.info("平台证书序列号: {} serialNo: {}", platSerialNo, serialNo);
        }
        // 根据证书序列号查询对应的证书来验证签名结果
        boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Bean.getPlatformCertPath());
        System.out.println("verifySignature:" + verifySignature);
        return body;
    }

    @Override
    public Object newPreOrderMini(OrderForm orderForm) throws Exception {
        CustomerEntity customer = customerDao.selectById(orderForm.getCustomerId());
        if(customer==null){
            throw new FitnessException(ExceptionEnum.CUSTOMER_NOT_FOUND);
        }else {
            if(StringUtils.isEmpty(customer.getOpenId())){
                throw new FitnessException(ExceptionEnum.CUSTOMER_OPENID_NOT_FOUND);
            }
        }
        OrderEntity order = orderService.preOrder(orderForm);
        log.info("直连jsApi支付被调用,openId={}",customer.getOpenId());
        String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
        UnifiedOrderModel unifiedOrderModel = new UnifiedOrderModel()
                .setAppid(wxPayV3Bean.getAppId())
                .setMchid(wxPayV3Bean.getMchId())
                .setDescription(order.getOrderDescription())
                .setOut_trade_no(order.getOrderNumber())
                .setTime_expire(timeExpire)
                .setAttach(order.getOrderDescription())
                .setNotify_url(wxPayV3Bean.getNotifyUrl())
                .setAmount(new Amount().setTotal(1))
                .setPayer(new Payer().setOpenid(customer.getOpenId()));
        log.info("统一下单参数 {}", JSONUtil.toJsonStr(unifiedOrderModel));
        IJPayHttpResponse response = WxPayApi.v3(
                RequestMethod.POST,
                WxDomain.CHINA.toString(),
                WxApiType.JS_API_PAY.toString(),
                wxPayV3Bean.getMchId(),
                getSerialNumber(),
                null,
                wxPayV3Bean.getKeyPath(),
                JSONUtil.toJsonStr(unifiedOrderModel)
        );
        // 根据证书序列号查询对应的证书来验证签名结果
        boolean verifySignature = WxPayKit.verifySignature(response, wxPayV3Bean.getPlatformCertPath());
        log.info("verifySignature: {}", verifySignature);
        log.info("统一下单响应 {}", response);

        //成功
        //修改订单状态
        order.setOrderStatus(OrderStatusEnum.UNPAID.getStatusName());
        orderDao.updateById(order);

        if (verifySignature) {
            String body = response.getBody();
            cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(body);
            String prepayId = jsonObject.getStr("prepay_id");
            Map<String, String> map = WxPayKit.jsApiCreateSign(wxPayV3Bean.getAppId(), prepayId, wxPayV3Bean.getKeyPath());
            log.info("唤起支付参数:{}", map);
            return map;
        }
        return response.getBody();
    }


    private String getSerialNumber() {
        if (StrUtil.isEmpty(serialNo)) {
            // 获取证书序列号
            X509Certificate certificate = PayKit.getCertificate(FileUtil.getInputStream(wxPayV3Bean.getCertPath()));
            serialNo = certificate.getSerialNumber().toString(16).toUpperCase();

//            System.out.println("输出证书信息:\n" + certificate.toString());
//            // 输出关键信息，截取部分并进行标记
//            System.out.println("证书序列号:" + certificate.getSerialNumber().toString(16));
//            System.out.println("版本号:" + certificate.getVersion());
//            System.out.println("签发者：" + certificate.getIssuerDN());
//            System.out.println("有效起始日期：" + certificate.getNotBefore());
//            System.out.println("有效终止日期：" + certificate.getNotAfter());
//            System.out.println("主体名：" + certificate.getSubjectDN());
//            System.out.println("签名算法：" + certificate.getSigAlgName());
//            System.out.println("签名：" + certificate.getSignature().toString());
        }
        System.out.println("serialNo:" + serialNo);
        return serialNo;
    }

    /**
     * 微信支付
     * @param associatedData
     * @param nonce
     * @param cipherText
     * @param certPath
     * @return
     */
    private String savePlatformCert(String associatedData, String nonce, String cipherText, String certPath) {
        try {
            AesUtil aesUtil = new AesUtil(wxPayV3Bean.getApiKey().getBytes(StandardCharsets.UTF_8));
            // 平台证书密文解密
            // encrypt_certificate 中的  associated_data nonce  ciphertext
            String publicKey = aesUtil.decryptToString(
                    associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8),
                    cipherText
            );
            // 保存证书
            FileWriter writer = new FileWriter(certPath);
            writer.write(publicKey);
            // 获取平台证书序列号
            X509Certificate certificate = PayKit.getCertificate(new ByteArrayInputStream(publicKey.getBytes()));
            return certificate.getSerialNumber().toString(16).toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
