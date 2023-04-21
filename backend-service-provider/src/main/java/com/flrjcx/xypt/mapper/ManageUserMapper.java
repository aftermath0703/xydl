package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.annotation.OpenPage;
import com.flrjcx.xypt.common.model.param.bbs.Impower;
import com.flrjcx.xypt.common.model.param.common.Users;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: aftermath
 * @Date: 2022-10-23 19:53:30
 * @Desc:
 */
public interface ManageUserMapper {
    /**
     * 获取用户列表
     *
     * @return
     */
    List<Users> getUserList();

    /**
     * 查询所有异常用户状态获取列表
     *
     * @return Users列表
     */
    List<Users> getUserListByStatus();

    /**
     * 根据用户状态获取列表
     *
     * @param status
     * @return
     */
    List<Users> getUserListByStatusNotNull(@Param("status") Integer status);

    /**
     * 查询用户详情
     *
     * @param userId
     * @return
     */
    Users getUserInfo(@Param("userId") long userId);

    long updateUser(Users user);

    /**
     * 封禁用户
     *
     * @param userId
     * @param banReason
     * @return
     */
    boolean deleteUser(@Param("userId") long userId, @Param("banReason") String banReason);

    List<Users> getUserListByRegisterTime(@Param("begin") String begin, @Param("end") String end);

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
     * @param userId
     */
    void rescindUser(@Param("userId") long userId);

    /**
     * 授权用户
     *
     * @param impower
     */
    void impowerUser(@Param("impower") Impower impower);

    /**
     * 取消授权用户
     *
     * @param userId
     */
    void cancelImpowerUser(@Param("userId") long userId);

    /**
     * 查询已授权用户
     *
     * @return
     */
    @OpenPage
    List<Impower> selectImpowerUser();

}