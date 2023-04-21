package com.flrjcx.xypt.common.utils;

import com.flrjcx.xypt.common.enums.ValidStatusEnum;
import com.flrjcx.xypt.common.model.param.common.TransactionParam;
import org.apache.commons.lang3.ObjectUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 订单、交易工具类
 *
 * @author Flrjcx
 */
public class OrderUtils {
    public static final String RECHARGE = "充值";
    public static final String DEPOSIT = "提现";
    public static final String SYSTEM_MESSAGE = "系统初始化";
    public static final int REWARD = 2;

    /**
     * 生成订单号
     *
     * @return
     */
    public static String orderNumber() {
        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSSSSSS");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    /**
     * 充值/提现(单方面交易)
     *
     * @param money:金额
     * @param userId:用户id
     * @param type:类型,充值/提现
     * @return
     */
    public static TransactionParam makeTransaction(BigDecimal money,Long userId,int type,BigDecimal balance){
        TransactionParam transactionParam = new TransactionParam();
        transactionParam.setTransactionId(orderNumber());
        transactionParam.setTransactionAmount(money);
        transactionParam.setTransactionUserId(userId);
        transactionParam.setTransactionBalance(balance);
        transactionParam.setTransactionType(type);
        if (Objects.equals(ValidStatusEnum.InValidStatus.getCode(),type)){
            transactionParam.setTransactionContent(RECHARGE);
        }else {
            transactionParam.setTransactionContent(DEPOSIT);
        }
        return transactionParam;
    }

    public static TransactionParam makeTransaction(BigDecimal money,Long userId,int type,BigDecimal balance,Date time){
        TransactionParam transactionParam = new TransactionParam();
        transactionParam.setTransactionId(orderNumber());
        transactionParam.setTransactionAmount(money);
        transactionParam.setTransactionUserId(userId);
        transactionParam.setTransactionBalance(balance);
        transactionParam.setTransactionType(type);
        transactionParam.setTransactionCreateTime(time);
        transactionParam.setTransactionContent(SYSTEM_MESSAGE);
        return transactionParam;
    }

    /**
     * 打赏(有交易对象)
     *
     * @return
     */
    public static TransactionParam makeTransaction(BigDecimal money,Long userId,Long beUserId,String content,String nick,BigDecimal balance){
        TransactionParam transactionParam = new TransactionParam();
        transactionParam.setTransactionId(orderNumber());
        transactionParam.setTransactionAmount(money);
        transactionParam.setTransactionBeUserId(beUserId);
        transactionParam.setTransactionUserId(userId);
        transactionParam.setTransactionBalance(balance);
        transactionParam.setTransactionContent(content);
        transactionParam.setTransactionBeUserNick(nick);
        transactionParam.setTransactionType(REWARD);
        return transactionParam;
    }
}
