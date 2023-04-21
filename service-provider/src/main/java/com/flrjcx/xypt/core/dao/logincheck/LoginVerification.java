package com.flrjcx.xypt.core.dao.logincheck;

import com.flrjcx.xypt.common.model.param.register.LoginParam;

/**
 * @author malaka
 */
public interface LoginVerification {

    /**
     * 验证登录是否通过
     * @param loginParam 请求参数
     * @param objs 可能需要的参数
     * @return 验证是否通过
     */
    boolean verificationPassed(LoginParam loginParam, Object... objs);

}
