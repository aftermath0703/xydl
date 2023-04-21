package com.flrjcx.xypt.core.dao.logincheck.impl;

import com.flrjcx.xypt.common.enums.LoginTypeEnum;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.register.LoginParam;
import com.flrjcx.xypt.common.utils.EncryptUtils;
import com.flrjcx.xypt.core.dao.logincheck.AbstractLoginMapperCheck;
import com.flrjcx.xypt.core.dao.logincheck.LoginVerification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 手机号登录
 * @author malaka
 */
@Component(LoginTypeEnum.LOGIN_TYPE_PHONE_PASSWORD)
public class PhoneLoginCheck extends AbstractLoginMapperCheck implements LoginVerification {

    @Override
    public Users check(LoginParam loginParam) {
        Users users = loginMapper.findUserByPhone(loginParam.getUser());
        if (ObjectUtils.isEmpty(users)) {
            return users;
        }
        String dbPwd = users.getPassword();
        if (verificationPassed(loginParam, dbPwd)) {
            return users;
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
