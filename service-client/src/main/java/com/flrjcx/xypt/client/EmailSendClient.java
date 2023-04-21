package com.flrjcx.xypt.client;

import com.flrjcx.xypt.common.model.param.email.EmailSendParam;
import com.flrjcx.xypt.common.model.result.ResponseData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author malaka
 * @Description 邮箱
 * @date 2022/10/26 0:11
 */
@FeignClient("campus-email-service")
public interface EmailSendClient {

    /**
     * 注册邮箱验证
     * @param token
     * @return
     */
    @GetMapping("/xypt/api/email/verificationRegister")
    ResponseData verificationRegister(@RequestParam("token") String token);

    /**
     * 发送注册邮箱
     * @param param
     * @return
     */
    @PostMapping("/xypt/api/email/sendRegister")
    public ResponseData sendRegister(@RequestBody EmailSendParam param);


}
