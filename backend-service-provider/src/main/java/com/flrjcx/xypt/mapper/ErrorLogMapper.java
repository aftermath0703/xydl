package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.domain.log.ErrorLog;
import com.flrjcx.xypt.common.model.param.log.ErrorLogParam;

import java.util.List;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/11/5 20:59
 */
public interface ErrorLogMapper {
    /**
     * 查询异常日志
     * @param errorLogParam
     * @return
     */
    List<ErrorLog> selectErrorList(ErrorLogParam errorLogParam);
    /**
     * 查询异常日志总数
     * @param errorLogParam
     * @return
     */
    Long selectErrorListTotal(ErrorLogParam errorLogParam);
}
