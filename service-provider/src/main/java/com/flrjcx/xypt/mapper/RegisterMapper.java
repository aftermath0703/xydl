package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.register.AddUserParam;

/**
 * 注册
 *
 * @author Flrjcx
 */
public interface RegisterMapper {
    /**
     * 注册用户
     *
     * @param users
     */
    void addUser(Users users);

    /**
     * 通过账号查询用户
     * @param account
     * @return
     */
    Users selectUserByAccount(String account);
    /**
     * 通过邮箱查询用户
     * @param email
     * @return
     */
    Users selectUserByEmail(String email);

    /**
     * 查找数据库中是否有相同的邮箱
     *
     * @param email 邮箱
     * @return
     */
    boolean findSameEmail(String email);

    /**
     * 查找数据库中是否有相同的账户
     *
     * @param account 账户
     * @return
     */
    boolean findSameAccount(String account);
}
