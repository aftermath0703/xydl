package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.domain.log.ErrorLog;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/11/5 18:38
 */
public interface ErrorLogConsumerMapper {

    /**
     * 错误日志记录
     *
     * @param errorLog
     */
    void insertErrorLog(ErrorLog errorLog);

}
