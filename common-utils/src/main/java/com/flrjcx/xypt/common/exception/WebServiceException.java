package com.flrjcx.xypt.common.exception;

import com.flrjcx.xypt.common.model.result.ResponseData;

/**
 * 业务异常
 * @author malaka
 */
public abstract class WebServiceException extends RuntimeException {

    private static final long serialVersionUID = 768521430079465528L;

    protected ResponseData responseData;

    protected abstract ResponseData buildResponseData();

    public WebServiceException() {
        setResponseData(buildResponseData());
    }

    private void setResponseData(ResponseData responseData) {
        this.responseData = buildResponseData();
    }

    public ResponseData getResponseData() {
        return responseData;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }

}
