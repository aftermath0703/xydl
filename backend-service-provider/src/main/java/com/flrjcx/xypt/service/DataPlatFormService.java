package com.flrjcx.xypt.service;


import com.flrjcx.xypt.common.model.result.data.plat.form.ChartResult;

import java.math.BigDecimal;
import java.util.List;

/**
 * 数据平台
 *
 * @author Flrjcx
 */
public interface DataPlatFormService {
    /**
     * 获取平台总交易额度
     *
     * @return
     */
    BigDecimal totalTransaction();

    /**
     * 获取平台总支出额度(提现)
     *
     * @return
     */
    BigDecimal totalDeposit();

    /**
     * 获取平台总交易额度(图表)
     *
     * @param beforeTime:开始时间
     * @param afterTime:结束时间
     * @param date:年份
     * @return
     */
    List<ChartResult> totalTransactionChart(Long beforeTime, Long afterTime,String date);

    /**
     * 获取平台总支出额度(图表)
     *
     * @param beforeTime:开始时间
     * @param afterTime:结束时间
     * @param date:年份
     * @return
     */
    List<ChartResult> totalDepositChart(Long beforeTime,Long afterTime,String date);
}
