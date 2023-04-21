package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.dto.VerifyCodeDto;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.service.VerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

/**
 * 验证码
 * @author malaka
 */
@Api(tags = "验证码模块")
@ApiRestController("/verificationcode")
@Log4j2
public class VerificationCodeController {

    @Resource
    private VerificationCodeService verificationCodeService;

    @ApiOperation(value = "生成验证码")
    @GetMapping
    public ResponseData createVerificationCode() {
        VerifyCodeDto verificationCode = verificationCodeService.createVerificationCode();
        if (verificationCode == null) {
            ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR);
        }
        return ResponseData.buildResponse(verificationCode);
    }



    @GetMapping("/test")
    public ResponseData test() {
        VerifyCodeDto verificationCode = verificationCodeService.createVerificationCode();
        if (verificationCode == null) {
            ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR);
        }
        return ResponseData.buildResponse(verificationCode);
    }

}
