package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.model.param.bbs.Impower;
import com.flrjcx.xypt.common.model.param.common.Users;

import java.util.List;

/**
 * @Author: aftermath
 * @Date: 2022-10-23 19:49:11
 * @Desc:
 */
public interface ManageUserService {
    /**
     * 获取全部用户(分页)
     *
     * @return
     */
    List<Users> getUserList();

    /**
     * 查询异常用户列表
     *
     * @param status
     * @return
     */
    List<Users> getUserListByStatus(Integer status);

    long updateUser(Users user, String token);

    /**
     * 封禁用户
     *
     * @param userId
     * @param banReason
     * @return
     */
    boolean deleteUser(long userId,String banReason);

    Users getUserInfo(long userId);

    List<Users> getUserListByRegisterTime(String begin, String end);

    /**
     * 根据账号或昵称模糊查询列表
     *
     * @param account 账号或昵称
     * @return 用户列表
     */
    List<Users> findByNickNameOrAccount(String account);

    /**
     * 解除封禁用户
     *
     * @param userId:用户id
     */
    void rescindUser(long userId);

    /**
     * 授权用户
     *
     * @param userId
     */
    void impowerUser(long userId);

    /**
     * 取消授权用户
     *
     * @param userId
     */
    void cancelImpowerUser(long userId);

    /**
     * 查询已授权用户
     *
     * @return
     */
    List<Impower> selectImpowerUser();

}

