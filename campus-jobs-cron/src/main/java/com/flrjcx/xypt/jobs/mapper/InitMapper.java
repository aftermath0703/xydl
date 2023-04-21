package com.flrjcx.xypt.jobs.mapper;

import com.flrjcx.xypt.common.model.param.common.TransactionParam;
import org.apache.ibatis.annotations.Param;

/**
 * 初始化
 *
 * @author Flrjcx
 */
public interface InitMapper {
    /**
     * 每天初始化数据
     *
     * @param transactionParam
     */
    void initInsert(@Param("transactionParam") TransactionParam transactionParam);
}
