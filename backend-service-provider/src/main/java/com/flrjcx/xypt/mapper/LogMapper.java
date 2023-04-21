package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.model.result.log.ApiLogResult;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 日志服务
 *
 * @author Flrjcx
 */
public interface LogMapper {
    /**
     *获取Api访问日志列表
     *
     * @param beforeTime
     * @param afterTime
     * @param ip
     * @param uri
     * @param city
     * @return
     */
    @OpenPage
    List<ApiLogResult> getApiLogList(@Param("beforeTime") Date beforeTime, @Param("afterTime") Date afterTime
                                            , @Param("ip") String ip, @Param("uri")String uri, @Param("city")String city);
}
