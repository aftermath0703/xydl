package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.LoginTypeEnum;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.dto.LoginDto;
import com.flrjcx.xypt.common.model.param.register.LoginParam;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.CaptchaUtil;
import com.flrjcx.xypt.common.utils.TokenService;
import com.flrjcx.xypt.service.ManagerLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 登陆
 *
 * @author aftermath
 */
@Api(tags = "管理员登录模块")
@ApiRestController("/index")
@Log4j2
public class ManagerLoginController {
    @Resource
    private CaptchaUtil captchaUtil;

    @Resource
    private TokenService tokenService;

    @Resource
    private ManagerLoginService managerLoginService;


    @Validation
    @ApiOperation("管理员退出登录")
    @PostMapping("/logout")
    public ResponseData logout(@RequestHeader("Authorization") String token) {
        tokenService.removeManagerToken(token);
        return ResponseData.buildSuccess();
    }

    @ApiOperation(value = "管理员登录")
    @PostMapping("/login")
    public ResponseData login(@RequestBody LoginParam loginParam) {
        log.info("");
        try {
            if (Objects.isNull(loginParam.getUser())) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_NAME_REQUIRED);
            }
            if (Objects.isNull(loginParam.getUserPassword())) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_PASSWORD_ERROR);
            }
            if (Objects.isNull(loginParam.getUuid())) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_VERIFICATION_REQUIRED);
            }
            // 检查验证码
            if (!captchaUtil.verification(loginParam.getUuid(), loginParam.getVerifyCode())) {
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_VERIFICATION_ERROR_CODE);
            }
            if (Objects.isNull(loginParam.getLoginType())) {
                // 默认账号密码方式登录
                loginParam.setLoginType(LoginTypeEnum.LOGIN_TYPE_ACCOUNT_PASSWORD);
            }

            LoginDto login = managerLoginService.login(loginParam);
            if (!ObjectUtils.isEmpty(login)) {
                return ResponseData.buildResponse(login);
            }else {
                return ResponseData.buildErrorResponse(ResultCodeEnum.USER_LOGIN_PWD_ERROR_CODE);
            }
        } catch (Exception e) {
            log.error("/login error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }
}
