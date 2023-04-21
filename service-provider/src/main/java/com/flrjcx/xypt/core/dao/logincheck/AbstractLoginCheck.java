package com.flrjcx.xypt.core.dao.logincheck;

import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.register.LoginParam;

/**
 * 登录检查
 * @author malaka
 */
public abstract class AbstractLoginCheck {

    /**
     * 检查登录
     * @param loginParam 用户请求输入信息
     * @return 查询出来的对象
     */
    public abstract Users check(LoginParam loginParam);


}
