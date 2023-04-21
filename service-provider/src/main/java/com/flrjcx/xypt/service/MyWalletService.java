package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.model.param.common.TransactionParam;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * 我的钱包
 *
 * @author Flrjcx
 */
public interface MyWalletService {

    /**
     * 充值/提现
     *
     * @param money:金额
     * @param type:类型
     * @return
     */
    int recharge(BigDecimal money,int type);

    /**
     * 获取用户S币
     *
     * @return
     */
    BigDecimal getBalance();


    /**
     * 资金明细
     *
     * @param time
     * @param type
     * @return
     */
    List<TransactionParam> moneyDetails(Long time,Integer type);
}
