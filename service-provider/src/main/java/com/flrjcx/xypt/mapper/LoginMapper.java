package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.param.common.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 登录
 *
 * @author Flrjcx
 */
public interface LoginMapper {
    /**
     * 获取全部用户
     *
     * @return
     */
    List<Users> testUserList();

    /**
     * 通过手机查询用户
     * @param phone 手机号
     * @return 查询到的用户
     */
    Users findUserByPhone(String phone);

    /**
     * 通过邮箱查询用户
     * @param email 邮箱
     * @return 用户
     */
    Users findUserByEmail(String email);

    /**
     * 通过账号查询用户
     * @param account 账号
     * @return 用户
     */
    Users findUserByAccount(String account);

    /**
     * 根据userId修改密码
     * @param userId
     * @param md5AndBCryptPassword
     * @return
     */
    int updateUserPasswordById(Long userId, String md5AndBCryptPassword);

    /**
     * 查询账户的封禁状态
     *
     * @param account
     * @return
     */
    String checkUserStatus(@Param("account") String account);
}
