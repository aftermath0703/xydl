package com.flrjcx.xypt.common.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具类
 * @author malaka
 */
@Component
public class JWTUtils {

    @Value("token.secret")
    private String jwtToken;

    private final static Long ONE_DAY = 24 * 60 * 60 * 1000L;

    public String createToken(Long userId){
        Map<String,Object> claims = new HashMap<>();
        claims.put("userId",userId);
        JwtBuilder jwtBuilder = Jwts.builder()
                // 签发算法，秘钥为jwtToken
                .signWith(SignatureAlgorithm.HS256, jwtToken)
                // body数据，要唯一，自行设置
                .setClaims(claims)
                // 设置签发时间
                .setIssuedAt(new Date())
                // 一天的有效时间
                .setExpiration(new Date(System.currentTimeMillis() + ONE_DAY));
        String token = jwtBuilder.compact();
        return token;
    }

    public Map<String, Object> checkToken(String token){
        try {
            Jwt parse = Jwts.parser().setSigningKey(jwtToken).parse(token);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

}
