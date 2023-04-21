package com.flrjcx.xypt.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.flrjcx.xypt.client.EmailSendClient;
import com.flrjcx.xypt.common.enums.CacheTokenEnum;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.model.dto.LoginDto;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.model.param.email.EmailSendParam;
import com.flrjcx.xypt.common.model.param.register.AddUserParam;
import com.flrjcx.xypt.common.model.result.ResponseData;
import com.flrjcx.xypt.common.utils.*;
import com.flrjcx.xypt.mapper.RegisterMapper;
import com.flrjcx.xypt.service.RegisterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 注册实现类
 *
 * @author Flrjcx
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Resource
    private RegisterMapper registerMapper;

    @Resource
    private SnowflakeIdWorker snowflakeIdWorker;

    @Resource
    private TokenService tokenService;

    @Resource
    private KafkaUtils kafkaUtils;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseData addUser(AddUserParam param) {
        // 注册
        Users user = new Users();
        user.setUserId(snowflakeIdWorker.nextId());
        user.setAccount(param.getAccount());
        user.setNickName(param.getAccount());
        user.setEmail(param.getEmail());
        user.setPassword(EncryptUtils.md5AndBCrypt(param.getPassword()));
        user.setStatus("1");
        System.out.println(JSON.toJSONString(user));
        registerMapper.addUser(user);
        String token = tokenService.createToken(user);

        return ResponseData.buildResponse(new LoginDto(token));
    }

    /**
     * 发送注册邮件
     *
     * @param param
     * @return
     */
    @Override
    public LoginDto sendMail(EmailSendParam param) {
        // 检查数据库有无当前邮箱
        if (registerMapper.selectUserByEmail(param.getAddress()) != null) {
            return null;
        }
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        param.setCode(String.valueOf(code));
        // 创建验证token
        String token = tokenService.createToken(CacheTokenEnum.CACHE_EMAIL, param);
        param.setToken(token);
        kafkaUtils.sendMessage(KafkaTopicEnum.TOPIC_EMAIL_SEND_REGISTER, JSON.toJSONString(param));
        return new LoginDto(token);
    }

    /**
     * 查找数据库中是否有相同的邮箱
     *
     * @param email
     * @return
     */
    @Override
    public boolean findSameEmail(String email) {
        return registerMapper.findSameEmail(email);
    }

    /**
     * 查找数据库中是否有相同的账户
     *
     * @param account
     * @return
     */
    @Override
    public boolean findSameAccount(String account) {
        return registerMapper.findSameAccount(account);
    }
}
