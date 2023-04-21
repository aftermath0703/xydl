package com.flrjcx.xypt.mapper;


import com.flrjcx.xypt.common.model.param.common.Manager;

/**
 * 管理员登陆模块
 *
 * @author aftermath
 */
public interface ManagerLoginMapper {
    /**
     * 通过账号查询用户
     * @param account 账号
     * @return 用户
     */
    Manager findManagerByAccount(String account);
}
