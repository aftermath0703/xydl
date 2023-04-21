package com.flrjcx.xypt.service;

import com.flrjcx.xypt.common.model.dto.MyInfoDto;
import com.flrjcx.xypt.common.model.dto.UserInfoDto;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.personal_center.RealNameParam;

/**
 * 个人中心
 *
 * @author Flrjcx
 */
public interface PersonalCenterService {

    /**
     * 实名注册接口
     * @param realNameParam 实名注册参数
     */
    void realUser(RealNameParam realNameParam);

    /**
     * 用户粉丝数量接口
     * @param userId 当前用户id
     * @return 当前用户粉丝数量
     */
    Integer getUserFansNum(Long userId);

    /**
     * 根据id获取real_register表的用户记录数
     * @param realRegisterUserId 用户id
     * @return 用户记录数
     */
    Integer realRegisterUserCount(Long realRegisterUserId);

    /**
     * 根据用户id 修改用户头像
     * @param picPath   头像访问路径
     * @param userId    当前用户id
     */
    void updateUserFace(String picPath, Long userId);


    /**
     * 用户详情接口
     * @param userId 当前用户id
     * @return
     */
    UserInfoDto getUserInfo(Long userId);

    /**
     * 个人详情接口
     * @param userId 当前用户id
     * @return
     */
    MyInfoDto getMyInfo(Long userId);

    /**
     * 账户注销邮箱验证码发送接口
     * @param userId 当前用户id
     * @param email 该用户邮箱账号
     * @return
     */
    boolean sendAccountDeleteMail(Long userId, String email);

    /**
     * 账户注销
     * @param userId 当前用户id
     * @param validateCode 用户输入验证码
     * @return
     */
    boolean deletedAccount(Long userId, String validateCode);

    /**
     * 更新用户信息
     * @param user 新的用户信息
     * @return
     */
    void updateUserInfo(Users user);
}
