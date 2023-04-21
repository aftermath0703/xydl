package com.flrjcx.xypt.common.exception;

import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.result.ResponseData;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * 使用 ResultCodeEnum 返回
 * @author malaka
 */
public class WebServiceEnumException extends WebServiceException implements Serializable {

    private static final long serialVersionUID = -637171558794830996L;

    private ResultCodeEnum resultCodeEnum;

    private WebServiceEnumException() {

    }

    @Override
    protected ResponseData buildResponseData() {
        return ResponseData.buildErrorResponse(resultCodeEnum);
    }

    public static WebServiceException buildResponseData(ResultCodeEnum codeEnum) {
        return new WebServiceException() {
            @Override
            protected ResponseData buildResponseData() {
                return ResponseData.buildResponse(codeEnum);
            }
        };
    }

}
