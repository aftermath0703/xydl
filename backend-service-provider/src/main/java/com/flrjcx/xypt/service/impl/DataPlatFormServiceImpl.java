package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.model.result.data.plat.form.ChartResult;
import com.flrjcx.xypt.common.utils.DateUtils;
import com.flrjcx.xypt.mapper.DataPlatFormMapper;
import com.flrjcx.xypt.service.DataPlatFormService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * 数据平台
 *
 * @author Flrjcx
 */
@Service
public class DataPlatFormServiceImpl implements DataPlatFormService {

    @Resource
    private DataPlatFormMapper dataPlatFormMapper;

    public static final String DATE_NULL = "DATE_NULL";


    /**
     * 获取平台总交易额度
     */
    @Override
    public BigDecimal totalTransaction() {
        return dataPlatFormMapper.totalTransaction();
    }

    /**
     * 获取平台总支出额度(提现)
     *
     * @return
     */
    @Override
    public BigDecimal totalDeposit() {
        return dataPlatFormMapper.totalDeposit();
    }

    /**
     * 获取平台总交易额度(图表)
     *
     * @param beforeTime:开始时间
     * @param afterTime:结束时间
     * @param date:年份
     * @return
     */
    @Override
    public List<ChartResult> totalTransactionChart(Long beforeTime, Long afterTime,String date) {
        if (ObjectUtils.equals(date,DATE_NULL)){
            return dataPlatFormMapper.totalTransactionChart(DateUtils.dateToStamp(beforeTime),DateUtils.dateToStamp(afterTime));
        }
        return dataPlatFormMapper.totalTransactionChartYear((date));
    }

    /**
     * 获取平台总支出额度(图表)
     *
     * @param beforeTime:开始时间
     * @param afterTime:结束时间
     * @param date:年份
     * @return
     */
    @Override
    public List<ChartResult> totalDepositChart(Long beforeTime, Long afterTime,String date) {
        if (ObjectUtils.equals(date,DATE_NULL)){
            return dataPlatFormMapper.totalDepositChart(DateUtils.dateToStamp(beforeTime),DateUtils.dateToStamp(afterTime));
        }
        return dataPlatFormMapper.totalDepositChartYear((date));
    }


}

