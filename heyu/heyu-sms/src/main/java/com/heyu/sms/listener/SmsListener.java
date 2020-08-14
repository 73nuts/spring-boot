package com.heyu.sms.listener;


import com.aliyuncs.exceptions.ClientException;
import com.heyu.sms.comfig.SmsProperties;
import com.heyu.sms.utils.SmsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Component
public class SmsListener {


    @Autowired
    private SmsUtils smsUtils;

    @Autowired
    private SmsProperties smsProperties;

    /**
     * @Description: 发送短信
     * @Param: [msg]
     * @return: void
     * @Author: Big Brother
     * @Date: 2020/7/29
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "HEYU.SMS.QUEUE", durable = "true"),
            exchange = @Exchange(value = "HEYU.ITEM.EXCHANGE", ignoreDeclarationExceptions = "true", type = ExchangeTypes.TOPIC),
            key = {"verifycode.sms"}
    ))
    public void sendSms(Map<String, String> msg) throws ClientException {
        if (CollectionUtils.isEmpty(msg)) {
            return;
        }
        //获取手机号
        String phone = msg.get("phone");
        //获取验证码
        String code = msg.get("code");
        //判断是否为空
        if (StringUtils.isNoneBlank(phone) && StringUtils.isNoneBlank(code)) {
            //若不为空则发送短信
            this.smsUtils.sendSms(phone, code, this.smsProperties.getSignName(), this.smsProperties.getVerifyCodeTemplate());
        }
    }
}
