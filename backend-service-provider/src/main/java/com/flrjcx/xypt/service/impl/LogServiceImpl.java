package com.flrjcx.xypt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.flrjcx.xypt.common.model.result.ip.DetailsIpParam;
import com.flrjcx.xypt.common.model.result.log.ApiLogResult;
import com.flrjcx.xypt.common.utils.DateUtils;
import com.flrjcx.xypt.common.utils.HttpPoolUtils;
import com.flrjcx.xypt.mapper.LogMapper;
import com.flrjcx.xypt.service.LogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * api接口访问日志
 *
 * @author Flrjcx
 */
@Service
public class LogServiceImpl implements LogService {

    public static final String DETAILS_IP = "http://whois.pconline.com.cn/ipJson.jsp";
    @Resource
    private LogMapper logMapper;

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
    @Override
    public List<ApiLogResult> getApiLogList(Long beforeTime, Long afterTime, String ip, String uri, String city) {
        return logMapper.getApiLogList(DateUtils.dateToStamp(beforeTime),DateUtils.dateToStamp(afterTime),ip,uri,city);
    }

    /**
     * 查询ip详情信息
     * @param ip
     * @return
     */
    @Override
    public DetailsIpParam detailsIp(String ip) {
        Map<String , Object> param = new HashMap<>();
        param.put("ip",ip);
        param.put("json","true");
        String s = HttpPoolUtils.get(DETAILS_IP, param, null);
        return JSONObject.parseObject(s, DetailsIpParam.class);
    }
}
