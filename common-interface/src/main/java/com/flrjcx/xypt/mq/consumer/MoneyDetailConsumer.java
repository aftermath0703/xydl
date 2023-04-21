package com.flrjcx.xypt.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.model.param.common.TransactionParam;
import com.flrjcx.xypt.common.utils.DateUtils;
import com.flrjcx.xypt.mapper.MoneyDetailMapper;
import lombok.extern.slf4j.Slf4j;
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
public class MoneyDetailConsumer {
    @Resource
    private MoneyDetailMapper moneyDetailMapper;

    @KafkaListener(topics = KafkaTopicEnum.TOPIC_MONEY_SEND_DETAIL,groupId = "MONEY")
    @Async
    public void consumerLogMsg(ConsumerRecord<String, String> record, Acknowledgment ack){
        TransactionParam transactionParam = JSON.parseObject(record.value(), TransactionParam.class);
        transactionParam.setTransactionCreateTime(DateUtils.dateToStamp(record.timestamp()));
        moneyDetailMapper.moneyDetail(transactionParam);
        ack.acknowledge();
    }

}
