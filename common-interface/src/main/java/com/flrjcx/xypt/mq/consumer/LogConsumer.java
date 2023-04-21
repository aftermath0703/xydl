package com.flrjcx.xypt.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.model.domain.log.OperLog;
import com.flrjcx.xypt.common.model.result.ip.IpLocalResult;
import com.flrjcx.xypt.common.model.result.log.ApiLogResult;
import com.flrjcx.xypt.common.utils.DateUtils;
import com.flrjcx.xypt.common.utils.HttpPoolUtils;
import com.flrjcx.xypt.mapper.LogConsumerMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * kafka消费者
 *
 * @author Flrjcx
 */
@Component
@Slf4j
public class LogConsumer {

    @Resource
    private LogConsumerMapper logConsumerMapper;

    public static final String LOCALHOST = "127.0.1.1";
    public static final String LOCALHOST2 = "0:0:0:0:0:0:0:1";
    public static final String LOCALHOST3 = "127.0.0.1";

    @KafkaListener(topics = KafkaTopicEnum.TOPIC_API_LOG_SEND_MESSAGE, groupId = "LOG")
    @Async
    public void consumerApiLogMsg(ConsumerRecord<String, String> record, Acknowledgment ack) throws InterruptedException {
//        取出kafka消息,反序列化
        ApiLogResult logResult = JSON.parseObject(record.value(), ApiLogResult.class);
        if (ObjectUtils.equals(logResult.getIp(),LOCALHOST) || ObjectUtils.equals(logResult.getIp(),LOCALHOST2) || ObjectUtils.equals(logResult.getIp(),LOCALHOST3)){
            ack.acknowledge();
            return;
        }
        log.info("Topic:"+record.topic()+", value:"+record.value()+", time:"+record.timestamp());
        logResult.setTimestamp(DateUtils.dateToStamp(record.timestamp()));
//        将ip进行查询
        String ipLocalResultJson = HttpPoolUtils.ipLocal(logResult.getIp());
//        将查询结果反序列化
        IpLocalResult ipLocalResultObject = JSON.parseObject(ipLocalResultJson, IpLocalResult.class);

        if (ObjectUtils.isNotEmpty(ipLocalResultObject.getData().getIsp())){
            logResult.setIsp(ipLocalResultObject.getData().getIsp());
        }
        if (ObjectUtils.isNotEmpty(ipLocalResultObject.getData().getArea())){
            logResult.setArea(ipLocalResultObject.getData().getArea());
        }
        if (ObjectUtils.isNotEmpty(ipLocalResultObject.getData().getRegion())){
            logResult.setRegion(ipLocalResultObject.getData().getRegion());
        }
        if (ObjectUtils.isNotEmpty(ipLocalResultObject.getData().getCity())){
            logResult.setCity(ipLocalResultObject.getData().getCity());
        }
        if (ObjectUtils.isNotEmpty(ipLocalResultObject.getData().getDistrict())){
            logResult.setDistrict(ipLocalResultObject.getData().getDistrict());
        }

        logConsumerMapper.insertApiMsg(logResult);
        ack.acknowledge();

    }

    @KafkaListener(topics = KafkaTopicEnum.TOPIC_OPER_LOG_SEND_MESSAGE, groupId = "LOG")
    @Async
    public void consumerOperLogMsg(ConsumerRecord<String, String> record, Acknowledgment ack) {
        OperLog operLog = JSON.parseObject(record.value(), OperLog.class);
        //String operIp = operLog.getOperIp();
        //if (LOCALHOST.equals(operIp) || LOCALHOST2.equals(operIp) || LOCALHOST3.equals(operIp)) {
        //    ack.acknowledge();
        //    return;
        //}
        log.info("Topic:"+record.topic()+", value:"+record.value()+", time:"+record.timestamp());
        operLog.setOperTime(DateUtils.dateToStamp(record.timestamp()));
        logConsumerMapper.insertOperMsg(operLog);
        ack.acknowledge();
    }
}
