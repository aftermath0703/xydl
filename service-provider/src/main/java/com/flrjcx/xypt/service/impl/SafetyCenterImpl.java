package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.model.domain.safetycenter.UserPrivacy;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.email.ModifyPasswordEmailSendParam;
import com.flrjcx.xypt.common.utils.*;
import com.flrjcx.xypt.mapper.SafetyCenterMapper;
import com.flrjcx.xypt.service.SafetyCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 个人中心
 *
 * @author Flrjcx
 */
@Service
@Slf4j
public class SafetyCenterImpl implements SafetyCenterService {
    private static final String CACHE_PREFIX = "xypt:SafetyCenter";

    private static final String MAIL_PREFIX = "xypt:SafetyCenter:resetPasswordMail";

    @Resource
    private RedisCache redisCache;
    @Resource
    private SafetyCenterMapper safetyCenterMapper;

    @Resource
    private TokenService tokenService;

    @Resource
    private KafkaUtils kafkaUtils;

    /**
     * 向toMail发送重置密码邮件
     *
     * @param toMail
     * @return
     */
    @Override
    public boolean sendResetPassWordMail(Long userId, String toMail) {
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        String mailPrefix = MAIL_PREFIX + ':' + userId;
        try {
            redisCache.setCacheObject(mailPrefix, code.toString(), 5, TimeUnit.MINUTES);
            ModifyPasswordEmailSendParam param = new ModifyPasswordEmailSendParam();
            param.setCode(code.toString());
            param.setAddress(toMail);
            kafkaUtils.sendMessage(KafkaTopicEnum.TOPIC_EMAIL_SEND_MODIFY_PASSWORD, JsonUtils.fastJsonToString(param));
            log.info("向用户ID{}发送重置密码验证码邮件成功,邮箱为{},验证码为{}", userId, toMail, code);
        } catch (Exception e) {
            redisCache.deleteObject(mailPrefix);
            log.error("向用户ID{}发送重置密码验证码邮件失败,邮箱为{},验证码为{}", userId, toMail, code);
            return false;
        }
        return true;
    }

    /**
     * 检验用户的验证码，并修改密码
     *
     * @param userId         用户id
     * @param submitPassword 用户提交的新密码
     * @param validateCode   验证码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean modifyPassword(Long userId, String submitPassword, String validateCode) {
        String mailPrefix = MAIL_PREFIX + ':' + userId;
        String code = redisCache.getCacheObject(mailPrefix);
        if (StringUtils.isEmpty(code)) {
            log.info("无法在redis中找到修改密码的验证码");
            return false;
        }
        if (StringUtils.equals(validateCode, code)) {
            String md5AndBCryptPassword = EncryptUtils.md5AndBCrypt(submitPassword);
            int count = safetyCenterMapper.updateUserPasswordById(userId, md5AndBCryptPassword);
            if (count <= 0) {
                log.info("用户{}修改密码失败", userId);
                return false;
            }
            log.info("用户{}修改密码成功", userId);
            return true;
        }
        return false;
    }

    /**
     * 设置 不让别人看到我的XX
     *
     * @param userId  用户id
     * @param privacy 需要隐私的字段名
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean setPrivacy(Long userId, String privacy) {
        String privacySetting = safetyCenterMapper.selectUserPrivacySetting(userId);
        UserPrivacy userPrivacy;
        if (StringUtils.isEmpty(privacySetting)) {
            //新建用户隐私对象
            userPrivacy = new UserPrivacy();
        } else {
            userPrivacy = JsonUtils.fastJsonParse(privacySetting, UserPrivacy.class);
        }
        //设置隐私对象的值
        userPrivacy = getPrivacyUser(userPrivacy, privacy);
        //序列化userPrivacy
        String userPrivacyString = JsonUtils.fastJsonToString(userPrivacy);
        boolean savePrivacy = safetyCenterMapper.savePrivacy(userId, userPrivacyString) > 0;
        if (savePrivacy) {
            //若数据库未更新，则不更新缓存
            Users users = tokenService.getUserCache(TokenService.USER_TAG + userId);
            users.setPrivacySetting(userPrivacyString);
            tokenService.updateCache(TokenService.USER_TAG + userId, users);
        }
        return savePrivacy;
    }

    /**
     * 检查userPrivacy中需要修改的隐私字段
     *
     * @param userPrivacy 用户隐私对象
     * @param privacy     需要隐藏的字段名
     * @return 处理过的用户隐私字段
     */
    @Override
    public UserPrivacy getPrivacyUser(UserPrivacy userPrivacy, String privacy) {
        //反转用户的隐私设置
        if (StringUtils.equals(privacy, UserPrivacy.ADDRESS)) {
            userPrivacy.setAddress(userPrivacy.getAddress() == 0 ? 1 : 0);
        } else if (StringUtils.equals(privacy, UserPrivacy.PHONE)) {
            userPrivacy.setPhone(userPrivacy.getPhone() == 0 ? 1 : 0);
        } else if (StringUtils.equals(privacy, UserPrivacy.BIRTHDAY)) {
            userPrivacy.setBirthday(userPrivacy.getBirthday() == 0 ? 1 : 0);
        } else if (StringUtils.equals(privacy, UserPrivacy.EMAIL)) {
            userPrivacy.setEmail(userPrivacy.getEmail() == 0 ? 1 : 0);
        }
        return userPrivacy;
    }

    /**
     * 返回隐私处理的用户对象
     *
     * @param user 需要隐私处理的用户对象
     * @return user 隐私处理的用户对象
     */
    @Override
    public Users getPrivacyUser(Users user) {
        String privacyString = safetyCenterMapper.selectUserPrivacySetting(user.getUserId());
        UserPrivacy userPrivacy = JsonUtils.parse(privacyString, UserPrivacy.class);
        //处理user对象
        if (userPrivacy.getAddress() == 1) {
            user.setAddress(null);
        } else if (userPrivacy.getPhone() == 1) {
            user.setPhone(null);
        } else if (userPrivacy.getEmail() == 1) {
            user.setEmail(null);
        } else if (userPrivacy.getBirthday() == 1) {
            user.setBirthday(null);
        }
        return user;
    }

    /**
     * 返回隐私处理的用户对象
     *
     * @param users 需要隐私处理的用户对象
     * @return user 隐私处理的用户对象
     */
    @Override
    public List<Users> getPrivacyUsers(List<Users> users) {
        return users.stream().map(this::getPrivacyUser).collect(Collectors.toList());
    }

    /**
     * 返回用户隐私状态
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public UserPrivacy getUserPrivacy(Long userId) {
        String privacySettingString = safetyCenterMapper.selectUserPrivacySetting(userId);
        if (StringUtils.isEmpty(privacySettingString)) {
            return new UserPrivacy();
        }
        return JsonUtils.parse(privacySettingString, UserPrivacy.class);
    }

}
