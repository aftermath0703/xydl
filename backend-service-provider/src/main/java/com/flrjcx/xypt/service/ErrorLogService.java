package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.model.domain.log.ErrorLog;
import com.flrjcx.xypt.common.model.param.log.ErrorLogParam;

import java.util.List;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/11/5 20:56
 */
public interface ErrorLogService {

    /**
     * 查询异常日志列表
     * @param errorLogParam
     * @return
     */
    List<ErrorLog> errorList(ErrorLogParam errorLogParam);

    /**
     * 查询日志列表总数
     * @param errorLogParam
     * @return
     */
    Long errorListTotal(ErrorLogParam errorLogParam);


}
