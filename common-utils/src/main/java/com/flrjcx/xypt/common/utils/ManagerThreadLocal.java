package com.flrjcx.xypt.common.utils;

import com.flrjcx.xypt.common.model.param.common.Manager;

/**
 * 当前登录管理员
 * 使用：
 *      在Controller方法上标上注解@Validation表示该接口需要token验证
 *      验证通过后会把管理员信息保存在 ManagerThreadLocal 中
 *      调用 ManagerThreadLocal.get() 即可获取管理员
 *      为防止内存泄露，请使用完后调用 ManagerThreadLocal.remove() 方法
 *
 * @author malaka
 */
public class ManagerThreadLocal {

    private ManagerThreadLocal(){}

    private static final  ThreadLocal<Manager> LOCAL = new ThreadLocal<>();

    public static void put(Manager manager) {
        LOCAL.set(manager);
    }

    public static Manager get() {
        return LOCAL.get();
    }

    public static void remove() {
        LOCAL.remove();
    }


}
