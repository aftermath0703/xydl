package com.flrjcx.xypt.common.web.exception;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.exception.ValidationException;
import com.flrjcx.xypt.common.exception.WebServiceException;
import com.flrjcx.xypt.common.model.domain.log.ErrorLog;
import com.flrjcx.xypt.common.model.dto.log.ErrorLogDto;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.KafkaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author malaka
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.application.name}")
    private String applicationName;

    @Resource
    private KafkaUtils kafkaUtils;

    /**
     * 用户验证未通过
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseData handlerValidationException(ValidationException e) {
        return e.getResponseData();
    }

    /**
     * 业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(WebServiceException.class)
    public ResponseData handlerWebServiceException(WebServiceException e) {
        return e.getResponseData();
    }


    /**
     * 未知异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseData handlerException(Exception e, HttpServletRequest request) {
        try {
            ErrorLog errorLog = new ErrorLog(e, request);
            errorLog.setErrorServiceHost(applicationName);
            log.warn("error: {}, from: {}, uri: {}, message: {}",
                    errorLog.getErrorName(),
                    applicationName,
                    errorLog.getErrorUri(),
                    errorLog.getErrorMessage());
            e.printStackTrace();
            sendMessageAsync(KafkaTopicEnum.TOPIC_ERROR_LOG, JSONObject.toJSONString(errorLog));
        } catch (Exception err) {
            log.warn("error处理失败: {}", err.getMessage());
        }
        return ResponseData.buildErrorResponse(ResultCodeEnum.FAIL.getCode(), e.getMessage(), e);
    }

    @Async
    public void sendMessageAsync(String topic, String s) {
        kafkaUtils.sendMessage(topic, s);
    }

}
