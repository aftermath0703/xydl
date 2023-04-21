package com.flrjcx.xypt.service.impl;

import com.flrjcx.xypt.common.model.dto.LoginDto;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.email.ForgetPasswordParam;
import com.flrjcx.xypt.common.model.param.register.LoginParam;
import com.flrjcx.xypt.common.utils.*;
import com.flrjcx.xypt.core.dao.logincheck.AbstractLoginCheck;
import com.flrjcx.xypt.core.factory.LoginCheckFactory;
import com.flrjcx.xypt.mapper.LoginMapper;
import com.flrjcx.xypt.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 登录实现类
 *
 * @author Flrjcx
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    private static final String FORGET_PWD_MAIL_PREFIX = "xypt:login:forgetPasswordMail";
    
    @Resource
    private LoginMapper loginMapper;

    @Resource
    private LoginCheckFactory loginCheckFactory;

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisCache redisCache;
    @Resource
    private EmailSendUtils emailSendUtils;

    @Override
    public LoginDto login(LoginParam loginParam) {
        AbstractLoginCheck check = loginCheckFactory.getBean(loginParam.getLoginType());
        Users users = check.check(loginParam);
        if (ObjectUtils.isEmpty(users)) {
            return null;
        }
        String userToken = tokenService.createToken(users);
        return new LoginDto(userToken);
    }

    @Override
    public Users checkEmailIsExist(String email) {
        return loginMapper.findUserByEmail(email);
    }

    @Override
    public boolean sendForgetPasswordMail(String email, Long userId) {
        //生成验证码
        Integer validateCode = ValidateCodeUtils.generateValidateCode(4);
        //邮件标题
        String subject = "校园达论忘记密码";
        //邮件内容
        String body = "您正在进行忘记密码操作，验证码有效期五分钟，请确认无误后填入：";
        try {
            //封装存入redis的信息：用户id，验证码
            ForgetPasswordParam forgetPasswordParam = new ForgetPasswordParam(userId, null, null, validateCode.toString());
            //存入redis缓存，有效期5分钟
            redisCache.setCacheObject(FORGET_PWD_MAIL_PREFIX, forgetPasswordParam, 5, TimeUnit.MINUTES);
            emailSendUtils.sendMail(email, subject, body, validateCode);
            log.info("向用户ID{}发送忘记密码验证码邮件成功，邮箱为{}，验证码为{}", userId, email, validateCode);
        } catch (Exception exception) {
            //清楚缓存中的验证码
            redisCache.deleteObject(FORGET_PWD_MAIL_PREFIX);
            log.info("向用户ID{}发送忘记密码验证码邮件失败，邮箱为{}，验证码为{}", userId, email, validateCode);
            return false;
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean forgetPassword(String newPassword, String verifyCode) {
        ForgetPasswordParam cache = redisCache.getCacheObject(FORGET_PWD_MAIL_PREFIX);
        if (ObjectUtils.isEmpty(cache)){
            return false;
        }
        String cacheVerifyCode = cache.getVerifyCode();
        Long userId = cache.getUserId();
        if (StringUtils.isEmpty(cacheVerifyCode)) {
            log.info("无法在redis中找到忘记密码的验证码");
            return false;
        }
        if (StringUtils.equals(verifyCode, cacheVerifyCode)) {
            String md5AndBCryptPassword = EncryptUtils.md5AndBCrypt(newPassword);
            int count = loginMapper.updateUserPasswordById(userId, md5AndBCryptPassword);
            if (count <= 0) {
                log.info("用户{}忘记密码失败", userId);
                return false;
            }
            log.info("用户{}忘记密码成功", userId);
            return true;
        }
        return false;
    }
}
