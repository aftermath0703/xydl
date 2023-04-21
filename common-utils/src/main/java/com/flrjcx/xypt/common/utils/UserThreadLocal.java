package com.flrjcx.xypt.common.utils;

import com.flrjcx.xypt.common.model.param.common.Users;

/**
 * 当前登录用户
 * 使用：
 *      在Controller方法上标上注解@UserValidation表示该接口需要token验证
 *      验证通过后会把用户信息保存在 UserThreadLocal 中
 *      调用 UserThreadLocal.get() 即可获取用户
 *      为防止内存泄露，请使用完后调用 UserThreadLocal.remove() 方法
 *
 * @author malaka
 */
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final  ThreadLocal<Users> LOCAL = new ThreadLocal<>();

    public static void put(Users users) {
        LOCAL.set(users);
    }

    public static Users get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }


}
