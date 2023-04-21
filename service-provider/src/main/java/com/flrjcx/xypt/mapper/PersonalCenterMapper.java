package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.dto.MyInfoDto;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.personal_center.RealNameParam;

/**
 * 个人中心
 *
 * @author Flrjcx
 */
public interface PersonalCenterMapper {
    /**
     * 用户实名注册
     *
     * @param realNameParam 用户实名参数
     */
    void realUser(RealNameParam realNameParam);

    /**
     * 获取用户粉丝数量
     *
     * @param userId 当前用户id
     * @return 粉丝数量
     */
    Integer getFansNum(Long userId);

    /**
     * 根据id查询用户记录数
     *
     * @param realRegisterUserId 实名注册用户id
     * @return 数据库中的用户记录数
     */
    Integer countByUserId(Long realRegisterUserId);

    /**
     * 更换用户头像
     *
     * @param picPath 新头像地址
     * @param userId  当前用户id
     */
    void changeHeadPortrait(String picPath, Long userId);

    /**
     * 根据id查询用户详情
     * @param userId 当前用户id
     * @return
     */
    Users getUserById(Long userId);

    /**
     * 删除当前用户
     * @param userId 当前用户id
     * @return
     */
    Integer deletedAccount(Long userId);

    /**
     * 删除当前用户实名记录
     * @param userId 用户id
     * @return
     */
    Integer deletedRealAccount(Long userId);

    /**
     * 更新用户信息
     *
     * @param user 新的用户信息 userId必填
     * @return
     */
    void updateUserInfo(Users user);

    /**
     * 根据用户id获取我的信息
     * @param userId 我的id
     * @return
     */
    MyInfoDto getMyInfoById(Long userId);
}
