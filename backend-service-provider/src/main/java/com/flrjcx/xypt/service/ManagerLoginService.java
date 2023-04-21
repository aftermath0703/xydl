package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.model.dto.LoginDto;
import com.flrjcx.xypt.common.model.param.register.LoginParam;

/**
 * 管理员登录服务
 *
 * @author Flrjcx
 */
public interface ManagerLoginService {
    LoginDto login(LoginParam loginParam);
}
