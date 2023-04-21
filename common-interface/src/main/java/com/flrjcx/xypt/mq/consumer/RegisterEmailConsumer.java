package com.flrjcx.xypt.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.model.param.email.EmailSendParam;
import com.flrjcx.xypt.common.utils.EmailSendUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author malaka
 * @Description 发送注册邮箱
 * @date 2022/10/31 18:56
 */
@Component
@Slf4j
public class RegisterEmailConsumer {

    @Autowired
    private EmailSendUtils emailSendUtils;

    @KafkaListener(topics = KafkaTopicEnum.TOPIC_EMAIL_SEND_REGISTER)
    @Async
    public void sendMail(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        EmailSendParam param = JSON.parseObject(value, EmailSendParam.class);
        String address = param.getAddress();
        String code = param.getCode();
        log.info("send register to:{}, code:{}",  address, code);
        emailSendUtils.sendFixedMail(address, code);
        ack.acknowledge();
    }

}
