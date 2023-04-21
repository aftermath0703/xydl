package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.param.common.TransactionParam;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 通用mapper
 *
 * @author Flrjcx
 */
public interface MoneyDetailMapper {

    /**
     * 用户资金明细,平台总交易额
     *
     * @param transactionParam
     */
    void moneyDetail(@Param("transactionParam") TransactionParam transactionParam);

    /**
     * 打赏(减余额)
     *
     * @param userId
     * @param money
     */
    void reward(@Param("userId") Long userId,@Param("money") BigDecimal money);

    /**
     * 打赏(加余额)
     *
     * @param beUserId
     * @param money
     */
    void beReward(@Param("beUserId") Long beUserId,@Param("money") BigDecimal money);


}
