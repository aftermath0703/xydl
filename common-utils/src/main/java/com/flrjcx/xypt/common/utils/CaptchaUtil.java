package com.flrjcx.xypt.common.utils;

import cn.hutool.core.lang.UUID;
import com.flrjcx.xypt.common.model.dto.VerifyCodeDto;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


/**
 * @author malaka
 * 验证码生成工具类
 */
@Component
public class CaptchaUtil {

    @Autowired
    private RedisCache redisCache;

    private final static int CACHE_TIME_MINUTES = 2;

    private final static String KEY_PRE = "VERIFICATION_CODE:";

    /**
     * 验证
     * @param uuid uuid唯一标识
     * @param code 输入验证码
     * @return 验证通过
     */
    public boolean verification(String uuid, String code) {
        String key = getKey(uuid);
        if (redisCache.verifyKey(key)) {
            // 获取缓存的验证码
            String cacheCode = redisCache.getCacheObject(key);
            // 删除验证码
            redisCache.deleteObject(key);
            // 验证输入
            return cacheCode.equalsIgnoreCase(code);
        }else {
            return false;
        }
    }

    public VerifyCodeDto catchaImgCreator() throws IOException, FontFormatException {
        //生成文字验证码
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        specCaptcha.setCharType(Captcha.TYPE_DEFAULT);
        specCaptcha.setFont(Captcha.FONT_1);
        String text = specCaptcha.text().toLowerCase();

        //生成token
        String uuid = UUID.fastUUID().toString();
        System.out.println("\"uuid\": \"" + uuid + "\", \n" +
                "    \"verifyCode\": \"" + text + "\"");
        createToken(uuid, text);
        VerifyCodeDto verifyCodeDto = new VerifyCodeDto();
        verifyCodeDto.setImg(specCaptcha.toBase64(""));
        verifyCodeDto.setUuid(uuid);
        return verifyCodeDto;
    }

    /**
     * 创建token
     * @param uuid uuid
     * @param text 验证码
     */
    private void createToken(String uuid, String text) {
        String key = getKey(uuid);
        redisCache.setCacheObject(key, text, CACHE_TIME_MINUTES, TimeUnit.MINUTES);
    }


    /**
     * 使用uuid返回组装好的key
     * @param uuid uuid
     * @return key
     */
    private String getKey(String uuid) {
        return KEY_PRE + uuid;
    }

}