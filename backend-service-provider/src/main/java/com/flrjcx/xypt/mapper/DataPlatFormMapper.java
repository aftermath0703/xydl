package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.result.data.plat.form.ChartResult;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据平台
 *
 * @author Flrjcx
 */
public interface DataPlatFormMapper {
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
     * @return
     */
    List<ChartResult> totalTransactionChart(@Param("beforeTime") Date beforeTime, @Param("afterTime") Date afterTime);

    /**
     * 获取平台总支出额度(图表)
     *
     * @param beforeTime:开始时间
     * @param afterTime:结束时间
     * @return
     */
    List<ChartResult> totalDepositChart(@Param("beforeTime") Date beforeTime, @Param("afterTime") Date afterTime);

    /**
     * 获取平台总支出额度(图表),取年份
     *
     * @param date
     * @return
     */
    List<ChartResult> totalDepositChartYear(@Param("date") String date);

    /**
     * 获取平台总交易额度(图表),取年份
     *
     * @param date
     * @return
     */
    List<ChartResult> totalTransactionChartYear(@Param("date") String date);
}
