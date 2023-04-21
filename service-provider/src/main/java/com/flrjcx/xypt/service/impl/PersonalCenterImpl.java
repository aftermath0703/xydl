package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.model.dto.MyInfoDto;
import com.flrjcx.xypt.common.model.dto.UserInfoDto;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.personal_center.RealNameParam;
import com.flrjcx.xypt.common.utils.*;
import com.flrjcx.xypt.mapper.PersonalCenterMapper;
import com.flrjcx.xypt.service.PersonalCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 个人中心
 *
 * @author Flrjcx
 */
@Service
@Slf4j
public class PersonalCenterImpl implements PersonalCenterService {
    public static final String MAIL_PREFIX = "xypt:PersonalCenter:accountDeleteMail";

    @Resource
    private PersonalCenterMapper personalCenterMapper;
    @Resource
    private EmailSendUtils emailSendUtils;
    @Resource
    private RedisCache redisCache;
    @Resource
    private TokenService tokenService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void realUser(RealNameParam realNameParam) {
        //身份证号加密
        String encryptedIdCard = IdCardEncryptUtils.encryptIdCard(realNameParam.getIdCard());
        realNameParam.setIdCard(encryptedIdCard);
        personalCenterMapper.realUser(realNameParam);
    }

    @Override
    public Integer getUserFansNum(Long userId) {
        return personalCenterMapper.getFansNum(userId);
    }

    @Override
    public Integer realRegisterUserCount(Long realRegisterUserId) {
        return personalCenterMapper.countByUserId(realRegisterUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserFace(String picPath, Long userId) {

        personalCenterMapper.changeHeadPortrait(picPath,userId);

    }

    @Override
    public UserInfoDto getUserInfo(Long userId) {
        Users user = personalCenterMapper.getUserById(userId);
        UserInfoDto userInfo = new UserInfoDto();
        BeanUtils.copyProperties(user, userInfo);
        return userInfo;
    }

    /**
     * 个人详情接口
     *
     * @param userId 当前用户id
     * @return
     */
    @Override
    public MyInfoDto getMyInfo(Long userId) {
        return personalCenterMapper.getMyInfoById(userId);
    }

    @Override
    public boolean sendAccountDeleteMail(Long userId, String email) {
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        String mailPrefix = MAIL_PREFIX + ":" + userId;
        //邮件标题
        String subject = "校园达论账户注销";
        //邮件内容
        String body = "您正在进行注销此账户操作，验证码有效期五分钟，请确认无误后填入：";
        try {
            //将验证码存入redis缓存，有效期5分钟
            redisCache.setCacheObject(mailPrefix, validateCode, 5, TimeUnit.MINUTES);
            emailSendUtils.sendMail(email, subject, body, validateCode);
            log.info("向用户ID{}发送账户注销验证码邮件成功，邮箱为{}，验证码为{}", userId, email, validateCode);
        } catch (Exception exception) {
            //清楚缓存中的验证码
            redisCache.deleteObject(mailPrefix);
            log.info("向用户ID{}发送账户注销验证码邮件失败，邮箱为{}，验证码为{}", userId, email, validateCode);
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deletedAccount(Long userId, String validateCode) {
        String validateCode_cache = redisCache.getCacheObject(MAIL_PREFIX + ":" + userId).toString();
        try {
            if (validateCode_cache.equals(validateCode)){
                personalCenterMapper.deletedRealAccount(userId);
                int count = personalCenterMapper.deletedAccount(userId);
                if (count == 1){
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            log.info("用户{}注销账户失败", userId);
            return false;
        }
    }

    /**
     * 更新用户信息
     *
     * @param user 新的用户信息 userId必填
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(Users user) {
        user.setUserId(UserThreadLocal.get().getUserId());
        personalCenterMapper.updateUserInfo(user);
    }
}
