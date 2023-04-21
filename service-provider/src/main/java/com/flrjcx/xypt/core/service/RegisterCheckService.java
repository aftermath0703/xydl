package com.flrjcx.xypt.core.service;

import com.flrjcx.xypt.common.enums.CacheTokenEnum;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.param.email.EmailSendParam;
import com.flrjcx.xypt.common.model.param.register.AddUserParam;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.CheckUsersUtils;
import com.flrjcx.xypt.common.utils.TokenService;
import com.flrjcx.xypt.mapper.RegisterMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Objects;


/**
 * @author malaka
 * @Description TODO
 * @date 2022/10/25 20:57
 */
@Component
public class RegisterCheckService {

    @Resource
    private RegisterMapper registerMapper;

    @Resource
    private TokenService tokenService;

    public ResponseData checkParam(AddUserParam addUserParam) {
        String key = CacheTokenEnum.CACHE_EMAIL.getKey() + addUserParam.getToken();
        ResponseData responseData = checkEmpty(addUserParam);
        if (responseData != null) {
            return responseData;
        }
        responseData = checkReg(addUserParam);
        if (responseData != null) {
            return responseData;
        }
        responseData = checkToken(addUserParam);
        if (responseData != null) {
            return responseData;
        }
        responseData = checkDb(addUserParam);
        if (responseData != null) {
            return responseData;
        }
        tokenService.removeToken(key);
        return null;
    }

    private ResponseData checkToken(AddUserParam addUserParam) {
        addUserParam.setEmail("");
        String key = CacheTokenEnum.CACHE_EMAIL.getKey() + addUserParam.getToken();
        EmailSendParam emailSendParam = tokenService.getCache(key);
        if (ObjectUtils.isEmpty(emailSendParam) || !emailSendParam.getCode().equals(addUserParam.getCode())) {
            return ResponseData.buildErrorResponse(ResultCodeEnum.ERROR_CODE_EMAIL_VERIFICATION_ERROR_CODE);
        }
        addUserParam.setEmail(emailSendParam.getAddress());
        return null;
    }

    private ResponseData checkDb(AddUserParam addUserParam) {
        if (registerMapper.selectUserByAccount(addUserParam.getAccount()) != null) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_USER_IS_EXIST);
        }
        if (registerMapper.selectUserByEmail(addUserParam.getEmail()) != null) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_EMAIL_IS_EXIST);
        }
        return null;
    }



    private ResponseData checkReg(AddUserParam addUserParam) {
        if (!CheckUsersUtils.regexAccount(addUserParam.getAccount())) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_CODE_ACCOUNT_ERROR_CODE);
        }
        if (!CheckUsersUtils.regexPassword(addUserParam.getPassword())) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_CODE_PASSWORD_ERROR_CODE);
        }
        return null;
    }

    private ResponseData checkEmpty(AddUserParam addUserParam) {
        if (Objects.isNull(addUserParam)) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_CODE_NAME_REQUIRED);
        }
        if (StringUtils.isEmpty(addUserParam.getAccount())) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_CODE_NAME_REQUIRED);
        }
        if (StringUtils.isEmpty(addUserParam.getPassword())) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_CODE_PASSWORD_ERROR);
        }
        if (StringUtils.isEmpty(addUserParam.getToken())) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_CODE_EMAIL_VERIFICATION_ERROR_CODE);
        }
        if (StringUtils.isEmpty(addUserParam.getCode())) {
            return ResponseData.buildResponse(ResultCodeEnum.ERROR_CODE_EMAIL_VERIFICATION_ERROR_CODE);
        }
        return null;
    }


}
