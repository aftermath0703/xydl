package com.flrjcx.xypt.mapper;

import com.flrjcx.xypt.common.model.param.common.Users;
import org.apache.ibatis.annotations.Param;

/**
 * 安全中心
 *
 * @author gyyst
 */
public interface SafetyCenterMapper {
    /**
     * 通过用户id搜索用户所有信息
     *
     * @param userId
     * @return
     */
    Users selectUserById(@Param("userId") Long userId);

    /**
     * 通过id修改用户密码
     *
     * @param userId
     * @param password
     * @return
     */
    int updateUserPasswordById(@Param("userId") Long userId, @Param("password") String password);

    /**
     * 通过id查找用户的隐私设置
     *
     * @param userId
     * @return
     */
    String selectUserPrivacySetting(@Param("userId") Long userId);

    /**
     * 通过id添加用户隐私信息
     *
     * @param userPrivacyString 隐私信息Json字符串
     * @return
     */
    int savePrivacy(@Param("userId") Long userId, @Param("userPrivacyString") String userPrivacyString);
}
