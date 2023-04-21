package com.flrjcx.xypt.common.handler;

import com.alibaba.fastjson.JSONObject;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.model.result.log.ApiLogResult;
import com.flrjcx.xypt.common.utils.HttpPoolUtils;
import com.flrjcx.xypt.common.utils.KafkaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 请求拦截器
 *
 * @author Flrjcx
 */
@Component
@Slf4j
public class ApiInterceptor implements HandlerInterceptor {
    @Resource
    private KafkaUtils kafkaUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ApiLogResult logResult = new ApiLogResult();
        logResult.setIp(HttpPoolUtils.getIp(request));
        logResult.setPort(request.getServerPort());
        logResult.setUri(request.getRequestURI());
        String logJsonStr = JSONObject.toJSONString(logResult);
        System.out.println(logJsonStr);
        kafkaUtils.sendMessageAsync(KafkaTopicEnum.TOPIC_API_LOG_SEND_MESSAGE,logJsonStr);
        return true;
    }

}
