package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.model.dto.LoginDto;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.register.LoginParam;

import java.util.List;

/**
 * 登录服务
 *
 * @author Flrjcx
 */
public interface LoginService {

    LoginDto login(LoginParam loginParam);

    /**
     * 验证该邮箱是否存在账号
     * @param email 邮箱账号
     * @return
     */
    Users checkEmailIsExist(String email);

    /**
     * 发送忘记密码邮件
     * @param email 邮箱账号
     * @param userId 该邮箱所属用户id
     * @return
     */
    boolean sendForgetPasswordMail(String email, Long userId);

    /**
     * 忘记密码
     * @param newPassword 新密码
     * @param verifyCode 验证码
     * @return
     */
    boolean forgetPassword(String newPassword, String verifyCode);
}
