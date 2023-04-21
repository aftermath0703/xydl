package com.flrjcx.xypt.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/10/31 2:11
 */
@Component
public class KafkaUtils {

    public KafkaUtils(KafkaProperties kafkaProperties) {
        System.out.println(kafkaProperties.getProducer().getBufferMemory().toString());
    }

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void sendMessage(String topic, String key, String value) {
        kafkaTemplate.send(topic, key, value);
    }

    public void sendMessage(String topic, String value) {
        kafkaTemplate.send(topic, value);
    }

    @Async
    public void sendMessageAsync(String topic, String key, String value) {
        kafkaTemplate.send(topic, key, value);
    }

    @Async
    public void sendMessageAsync(String topic, String value) {
        kafkaTemplate.send(topic, value);
    }

}
