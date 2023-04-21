package com.flrjcx.xypt.controller;

import com.flrjcx.xypt.common.annotation.ApiRestController;
import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.enums.LoginTypeEnum;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.dto.LoginDto;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.email.ForgetPasswordParam;
import com.flrjcx.xypt.common.model.param.register.LoginParam;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.*;
import com.flrjcx.xypt.mapper.LoginMapper;
import com.flrjcx.xypt.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 登录
 *
 * @author Flrjcx
 */
@Api(tags = "登录模块")
@ApiRestController("/login")
@Log4j2
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private CaptchaUtil captchaUtil;

    @Resource
    private TokenService tokenService;

    @Resource
    private LoginMapper loginMapper;


    @Validation
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public ResponseData logout(@RequestHeader("Authorization") String token) {
        Users users = UserThreadLocal.get();
        tokenService.removeUserToken(token);
        return ResponseData.buildSuccess();
    }

    @ApiOperation(value = "登录功能")
    @PostMapping
    public ResponseData login(@RequestBody LoginParam loginParam) {
        try {
            if ("2".equals(loginMapper.checkUserStatus(loginParam.getUser()))){
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_BAN_USER);
            }
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

            LoginDto login = loginService.login(loginParam);
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

    @ApiOperation("发送忘记密码邮件")
    @PostMapping("/forgetPassword/sendMail")
    public ResponseData forgetPasswordSendMail(@RequestBody ForgetPasswordParam forgetPasswordParam){
        try {
            if (ObjectUtils.isEmpty(forgetPasswordParam)){
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY);
            }
            String email = forgetPasswordParam.getEmailAddress();
            if (ObjectUtils.isEmpty(email)){
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_EMAIL_REQUIRED);
            }
            //验证邮箱是否存在
            Users user = loginService.checkEmailIsExist(email);
            if (ObjectUtils.isEmpty(user)){
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_EMAIL_ERROR);
            }
            //发送邮箱验证码
            boolean result = loginService.sendForgetPasswordMail(email, user.getUserId());
            if (!result){
                return ResponseData.buildErrorResponse(ResultCodeEnum.FAIL);
            }
            return ResponseData.buildResponse();
        } catch (Exception e) {
            log.error("/forgetPassword/sendMail error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

    @ApiOperation("忘记密码")
    @PutMapping("/forgetPassword")
    public ResponseData forgetPassword(@RequestBody ForgetPasswordParam forgetPasswordParam){
        try {
            if (ObjectUtils.isEmpty(forgetPasswordParam)){
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_DELETE_FORM_UPDATE_EMPTY);
            }
            if (ObjectUtils.isEmpty(forgetPasswordParam.getNewPassword())){
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_PASSWORD_ERROR);
            }
            if (ObjectUtils.isEmpty(forgetPasswordParam.getVerifyCode())){
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_VERIFICATION_REQUIRED);
            }
            //重置密码
            boolean result = loginService.forgetPassword(forgetPasswordParam.getNewPassword(), forgetPasswordParam.getVerifyCode());
            if (!result){
                return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_FORGET_PASSWORD_FAIL);
            }
            return ResponseData.buildResponse();
        } catch (Exception e) {
            log.error("/forgetPassword error " + e.getMessage());
            return ResponseData.buildErrorResponse(ResultCodeEnum.CODE_SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

}
