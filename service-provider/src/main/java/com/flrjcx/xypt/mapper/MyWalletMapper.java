package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.param.common.TransactionParam;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 我的钱包
 *
 * @author Flrjcx
 */
public interface MyWalletMapper {
    /**
     * 充值
     *
     * @param money
     * @param userId
     */
    void recharge(@Param("money") BigDecimal money,@Param("userId") Long userId);

    /**
     * 提现
     *
     * @param money
     * @param userId
     */
    void deposit(@Param("money") BigDecimal money,@Param("userId") Long userId);

    /**
     * 检查当前金额是否小于提现金额
     *
     * @param money
     * @param userId
     * @return
     */
    Boolean checkMoney(@Param("money") BigDecimal money,@Param("userId") Long userId);

    /**
     * 获取用户S币
     *
     * @param userId
     * @return
     */
    BigDecimal getBalance(@Param("userId") Long userId);

    /**
     * 资金明细
     *
     * @param userId
     * @param date
     * @param type
     * @return
     */
    List<TransactionParam> moneyDetails(@Param("userId") Long userId,@Param("date") String date,@Param("type") Integer type);

    /**
     * 资金明细(打赏收入)
     *
     * @param userId
     * @param date
     * @return
     */
    List<TransactionParam> moneyDetailsTwo(@Param("userId") Long userId,@Param("date") String date);

}
