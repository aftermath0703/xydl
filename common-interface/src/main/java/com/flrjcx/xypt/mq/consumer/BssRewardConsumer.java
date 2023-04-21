package com.flrjcx.xypt.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.model.param.bbs.Bbs;
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
 * 论坛打赏
 *
 * @author Flrjcx
 */
@Component
@Slf4j
public class BssRewardConsumer {

    @Resource
    private MoneyDetailMapper moneyDetailMapper;


    @KafkaListener(topics = KafkaTopicEnum.TOPIC_BBS_REWARD_SEND,groupId = "REWARD")
    public void consumerLogMsg(ConsumerRecord<String, String> record, Acknowledgment ack){
        TransactionParam transactionParam = JSON.parseObject(record.value(), TransactionParam.class);
        transactionParam.setTransactionCreateTime(DateUtils.dateToStamp(record.timestamp()));
//        打赏
        moneyDetailMapper.reward(transactionParam.getTransactionUserId(),transactionParam.getTransactionAmount());
        moneyDetailMapper.beReward(transactionParam.getTransactionBeUserId(),transactionParam.getTransactionAmount());
//        记录到t_transaction表
        moneyDetailMapper.moneyDetail(transactionParam);
        ack.acknowledge();
    }
}
