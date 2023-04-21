package com.flrjcx.xypt.mapper;


import com.flrjcx.xypt.common.model.domain.log.OperLog;
import com.flrjcx.xypt.common.model.result.log.ApiLogResult;
import org.apache.ibatis.annotations.Param;

/**
 * 消费者mapper
 *
 * @author Flrjcx
 */
public interface LogConsumerMapper {
    /**
     * 将kafka数据写入数据库(apiLog)
     *
     * @param apiLogResult 访问日志实体类
     */
    void insertApiMsg(@Param("apiLogResult") ApiLogResult apiLogResult);

    /**
     * 将kafka数据写入数据库(operLog)
     *
     * @param operLog 操作日志实体类
     */
    void insertOperMsg(@Param("operLog") OperLog operLog);
}
