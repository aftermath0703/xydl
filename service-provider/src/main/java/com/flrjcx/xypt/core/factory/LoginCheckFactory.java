package com.flrjcx.xypt.core.factory;

import com.flrjcx.xypt.common.enums.LoginTypeEnum;
import com.flrjcx.xypt.core.dao.logincheck.AbstractLoginCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 获取登录检查对象
 * @author malaka
 */
@Component
public class LoginCheckFactory {

    @Autowired
    private Map<String, AbstractLoginCheck> map;

    public AbstractLoginCheck getBean(String name) {
        AbstractLoginCheck check = map.get(name);
        return check == null ? map.get(LoginTypeEnum.LOGIN_TYPE_ACCOUNT_PASSWORD) : check;
    }


}
