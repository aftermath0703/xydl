package com.flrjcx.xypt.common.exception;

import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.result.ResponseData;

/**
 * 用户验证异常
 * @author malaka
 */
public class ValidationException extends WebServiceException  {
    private static final long serialVersionUID = -637171558794830996L;

    @Override
    protected ResponseData buildResponseData() {
        return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_INTOKEN_ERROR);
    }
}
