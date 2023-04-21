package com.flrjcx.xypt.jobs.deposit;

import cn.hutool.core.date.DateUtil;
import com.flrjcx.xypt.common.utils.OrderUtils;
import com.flrjcx.xypt.jobs.mapper.InitMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 初始化平台每天的数据
 *
 * @author Flrjcx
 */
@Slf4j
@Component
public class InitJobs{
    @Resource
    private InitMapper initMapper;


    /**
     * 初始化每天平台交易额
     */
    @Scheduled(cron = "${custom.initTransaction.cron}")
    public void initTransaction() throws InterruptedException {
        for (int i = 0; i < 1; i++) {
            initData();
            Thread.sleep(1);
        }
    }
    public void initData(){
        long system = 999999999999999999L;
        log.info("总交易额数据初始化执行时间: " + DateUtil.now());
        initMapper.initInsert(OrderUtils.makeTransaction(new BigDecimal(0),system,0,new BigDecimal(0), new Date()));
        initMapper.initInsert(OrderUtils.makeTransaction(new BigDecimal(0),system,1,new BigDecimal(0), new Date()));
        log.info("总交易额数据初始化执行结束时间: " + DateUtil.now());
    }
}
