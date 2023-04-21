package com.flrjcx.xypt.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.model.domain.log.ErrorLog;
import com.flrjcx.xypt.mapper.ErrorLogConsumerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author malaka
 * @Description 异常日志消费者
 * @date 2022/11/5 18:28
 */
@Component
@Slf4j
public class ErrorLogConsumer {

    @Resource
    private ErrorLogConsumerMapper errorLogConsumerMapper;

    @Async
    @KafkaListener(topics = KafkaTopicEnum.TOPIC_ERROR_LOG)
    public void sendMail(ConsumerRecord<String, String> record, Acknowledgment ack) {
        String value = record.value();
        ErrorLog param = JSON.parseObject(value, ErrorLog.class);
        System.out.println(param);
        errorLogConsumerMapper.insertErrorLog(param);
        log.info("error log:{}.{}:{}",  param.getErrorClass(), param.getErrorMethod(), param.getErrorLineNumber());
        ack.acknowledge();
    }

}
