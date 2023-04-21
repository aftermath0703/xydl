package com.flrjcx.xypt.core.dao.logincheck;

import com.flrjcx.xypt.mapper.LoginMapper;

import javax.annotation.Resource;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/10/22 18:33
 */
public abstract class AbstractLoginMapperCheck extends AbstractLoginCheck {

    @Resource
    protected LoginMapper loginMapper;

}
