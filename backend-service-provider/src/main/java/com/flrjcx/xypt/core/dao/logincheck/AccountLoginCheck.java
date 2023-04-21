package com.flrjcx.xypt.core.dao.logincheck;

import com.flrjcx.xypt.common.enums.LoginTypeEnum;
import com.flrjcx.xypt.common.model.param.common.Manager;
import com.flrjcx.xypt.common.model.param.register.LoginParam;
import com.flrjcx.xypt.common.utils.EncryptUtils;
import com.flrjcx.xypt.mapper.ManagerLoginMapper;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * @Author: aftermath
 * @Date: 2022-10-22 20:26:14
 * @Desc:
 */
@Component(LoginTypeEnum.LOGIN_TYPE_ACCOUNT_PASSWORD)
public class AccountLoginCheck implements LoginVerification {
    @Resource
    ManagerLoginMapper managerLoginMapper;

    public Manager checkLogin(LoginParam loginParam) {
        Manager manager = managerLoginMapper.findManagerByAccount(loginParam.getUser());
        if (ObjectUtils.isEmpty(manager)) {
            return manager;
        }
        String dbPwd = manager.getManagerPassword();
        if (verificationPassed(loginParam, dbPwd)) {
            return manager;
        }else {
            return null;
        }
    }

    /**
     * 验证登录是否通过
     * 使用BCrypt加密验证
     * @param loginParam 请求参数
     * @param objs       传入数据库查询出来的用户密码
     * @return 验证是否通过
     */
    @Override
    public boolean verificationPassed(LoginParam loginParam, Object... objs) {
        if (EncryptUtils.check(loginParam.getUserPassword(), objs[0].toString())) {
            return true;
        }else {
            return false;
        }
    }

}

