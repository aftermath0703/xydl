package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.model.domain.safetycenter.UserPrivacy;
import com.flrjcx.xypt.common.model.param.common.Users;

import java.util.List;

/**
 * 个人中心
 *
 * @author Flrjcx
 */

public interface SafetyCenterService {


    /**
     * 向toMail发送重置密码邮件
     *
     * @param userId 用户id
     * @param toMail 用户邮箱
     * @return
     */
    boolean sendResetPassWordMail(Long userId, String toMail);

    /**
     * 检验用户的验证码，并修改密码
     *
     * @param userId         用户id
     * @param submitPassword 用户提交的新密码
     * @param validateCode   验证码
     * @return
     */
    boolean modifyPassword(Long userId, String submitPassword, String validateCode);

    /**
     * 通过id设置用户的隐私设置
     *
     * @param userId  用户id
     * @param privacy 需要隐私的字段名
     * @return
     */

    boolean setPrivacy(Long userId, String privacy);

    /**
     * 返回隐私处理过的隐私对象
     *
     * @param userPrivacy 用户隐私对象
     * @param privacy     需要隐藏的字段名
     * @return 处理过的用户隐私字段
     */
    UserPrivacy getPrivacyUser(UserPrivacy userPrivacy, String privacy);

    /**
     * 返回隐私处理的用户对象
     *
     * @param user 需要隐私处理的用户对象
     * @return user 隐私处理的用户对象
     */
    Users getPrivacyUser(Users user);

    /**
     * 返回隐私处理的用户对象
     *
     * @param users 需要隐私处理的用户对象
     * @return user 隐私处理的用户对象
     */
    List<Users> getPrivacyUsers(List<Users> users);

    /**
     * 返回用户隐私状态
     *
     * @param userId 用户id
     * @return
     */
    UserPrivacy getUserPrivacy(Long userId);
}
