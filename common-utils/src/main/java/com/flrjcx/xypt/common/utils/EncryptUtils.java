package com.flrjcx.xypt.common.utils;

import cn.hutool.crypto.digest.BCrypt;
import cn.hutool.crypto.digest.DigestUtil;

/**
 * 加密工具类
 *
 * @author malaka
 */
public class EncryptUtils {

    private static final String MD5_SALT = "SHu5dg%$SY";

    /**
     * 用来加密密码，先MD5加密，然后对加密后的数据进行BCrypt二次加密
     *
     * @param str 未加密密码
     * @return 加密后
     */
    public static String md5AndBCrypt(String str) {
        String md5 = md5(str);
        return bCrypt(md5);
    }

    /**
     * 用来加密密码，先MD5加密，然后对加密后的数据进行BCrypt二次加密
     *
     * @param str 未加密密码
     * @return 加密后
     */
    public static String bCrypt(String str) {
        return BCrypt.hashpw(str);
    }

    public static String md5(String str) {
        return DigestUtil.md5Hex(str + MD5_SALT);
    }


    /**
     * 检查密码是否一致
     *
     * @param plaintext 明文，前端需要进行md5加密
     * @param hashed    密文，数据库存储的是调用md5AndBCrypt加密后的值
     * @return 比对结果
     */
    public static boolean check(String plaintext, String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }

    public static void main(String[] args) {
        System.out.println(md5("123456"));
        System.out.println(md5AndBCrypt("123456"));
    }

}
