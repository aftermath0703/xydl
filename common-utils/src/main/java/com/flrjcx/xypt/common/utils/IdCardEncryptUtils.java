package com.flrjcx.xypt.common.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证加密工具类
 * @author &nbsp
 */
public class IdCardEncryptUtils {
    /**
     * 盐值
     */
    private static final String idCardSalt = "nbsp_idCard_Encrypt";

    /**
     * 加密
     * @param idCard 需加密的身份证号
     * @return
     */
    public static String encryptIdCard(String idCard){
        Map<String,Object> claims = new HashMap<>();
        claims.put("idCard",idCard);
        JwtBuilder jwtBuilder = Jwts.builder()
                // 签发算法，秘钥为idCardSalt
                .signWith(SignatureAlgorithm.HS256, idCardSalt)
                // body数据，要唯一，自行设置
                .setClaims(claims)
                // 设置签发时间
                .setIssuedAt(new Date());
        String encryptedIdCard = jwtBuilder.compact();
        return encryptedIdCard;
    }

    /**
     * 解密
     * @param encryptedIdCard 密文身份证号码
     * @return
     */
    public static Map<String, Object> decodeIdCard(String encryptedIdCard){
        try {
            Jwt parse = Jwts.parser().setSigningKey(idCardSalt).parse(encryptedIdCard);
            return (Map<String, Object>) parse.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        String encryptedIdCard = encryptIdCard("123456789987654321");
        System.out.println(encryptedIdCard);
        Map<String, Object> stringObjectMap = decodeIdCard(encryptedIdCard);
        String idCard = (String) stringObjectMap.get("idCard");
        System.out.println(idCard);
    }
}
