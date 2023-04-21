package com.flrjcx.xypt.service;


import com.flrjcx.xypt.common.model.result.ip.DetailsIpParam;
import com.flrjcx.xypt.common.model.result.log.ApiLogResult;

import java.util.List;

/**
 * api接口访问日志
 *
 * @author Flrjcx
 */
public interface LogService {
    /**
     * 获取Api访问日志列表
     *
     * @param beforeTime
     * @param afterTime
     * @param ip
     * @param uri
     * @param city
     * @return
     */
    List<ApiLogResult> getApiLogList(Long beforeTime, Long afterTime, String ip, String uri, String city);

    /**
     * 查询ip详情信息
     *
     * @param ip
     * @return
     */
    DetailsIpParam detailsIp(String ip);


}
