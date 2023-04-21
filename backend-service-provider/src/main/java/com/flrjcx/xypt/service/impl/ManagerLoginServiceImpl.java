package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.model.dto.LoginDto;
import com.flrjcx.xypt.common.model.param.common.Manager;
import com.flrjcx.xypt.common.model.param.register.LoginParam;
import com.flrjcx.xypt.common.utils.TokenService;
import com.flrjcx.xypt.core.dao.logincheck.AccountLoginCheck;
import com.flrjcx.xypt.service.ManagerLoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wby
 */
@Service
public class ManagerLoginServiceImpl implements ManagerLoginService {
    @Resource
    TokenService tokenService;

    @Resource
    AccountLoginCheck check;

    @Override
    public LoginDto login(LoginParam loginParam) {
        Manager manager = check.checkLogin(loginParam);
        String managerToken = tokenService.createToken(manager);
        return new LoginDto(managerToken);
    }
}
