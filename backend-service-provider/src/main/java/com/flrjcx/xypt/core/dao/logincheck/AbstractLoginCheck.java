package com.flrjcx.xypt.core.dao.logincheck;

import com.flrjcx.xypt.common.model.param.register.LoginParam;
import com.flrjcx.xypt.mapper.ManagerLoginMapper;

import javax.annotation.Resource;

/**
 * @Author: aftermath
 * @Date: 2022-10-22 20:50:09
 * @Desc:
 */
public abstract class AbstractLoginCheck<T> {

    @Resource
    protected ManagerLoginMapper managerLoginMapper;

    /**
     * 检查登录
     * @param loginParam 用户请求输入信息
     * @return 查询出来的对象
     */
    public abstract T checkLogin(LoginParam loginParam);


}
