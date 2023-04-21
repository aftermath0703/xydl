package com.flrjcx.xypt.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.model.param.email.ModifyPasswordEmailSendParam;
import com.flrjcx.xypt.common.utils.EmailSendUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author gyyst
 * @Description
 * @Create by 2022/10/31 22:28
 */
@Component
@Slf4j
public class ModifyPasswordEmailConsumer {

    @Resource
    private EmailSendUtils emailSendUtils;

    @KafkaListener(topics = KafkaTopicEnum.TOPIC_EMAIL_SEND_MODIFY_PASSWORD)
    @Async
    public void sendMail(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        ModifyPasswordEmailSendParam param = JSON.parseObject(value, ModifyPasswordEmailSendParam.class);
        String address = param.getAddress();
        String code = param.getCode();
        log.info("send register to:{}, code:{}", address, code);
        emailSendUtils.sendMail(address, "重置密码", "你的验证码为：", Integer.valueOf(code));
        ack.acknowledge();
    }

}
