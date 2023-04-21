package com.flrjcx.xypt.common.utils;

import com.alibaba.fastjson.JSON;
import com.flrjcx.xypt.common.enums.CacheTokenEnum;
import com.flrjcx.xypt.common.model.param.common.Manager;
import com.flrjcx.xypt.common.model.param.common.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author malaka
 */
@Component
public class TokenService {


    // 用户token
    public static final String USER_TAG = "CACHE_USER:";
    // 管理员
    public static final String MANAGER_TAG = "CACHE_MANAGER:";
    /**
     * 1秒
     */
    private static final long MILLIS_SECOND = 1000;
    /**
     * 1分钟
     */
    private static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    /**
     * 1天
     */
    private static final long MILLIS_DAY = 24 * 60 * MILLIS_MINUTE;
    /**
     * 令牌自定义标识
     */
    @Value("${token.header:Authorization}")
    private String header;
    /**
     * 令牌超时时间
     */
    @Value("${token.userExpireTime:1440}")
    private int userExpireTime;
    @Value("${token.managerExpireTime:30}")
    private int managerExpireTime;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private RedisCache redisCache;

    public String getHeader() {
        return header;
    }


    /**
     * 通过枚举创建token缓存
     */
    public String createToken(CacheTokenEnum tokenEnum, Object data) {
        String token = UUID.randomUUID().toString();
        String key = tokenEnum.getKey() + token;
        Long cacheTime = tokenEnum.getCacheTime();
        TimeUnit timeUnit = tokenEnum.getTimeUnit();
        redisCache.setCacheObject(key, data, cacheTime, timeUnit);
        return token;
    }

    /**
     * 创建token缓存
     */
    public String createToken(Users users) {
        Long userId = users.getUserId();
        String token = jwtUtils.createToken(userId);
        //CACHE_USER:eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2NjY1MjI2MTMsInVzZXJJZCI6NjQsImlhdCI6MTY2NjQzNjIxM30.EpaSpXP2EriwHugC_51tvpaLA9I5JgBBFdLWjxtkNY8:users{}
        String userTokenKey = getUserKey(token);
        String userIdKey = getUserKey(userId.toString());
        redisCache.setCacheObject(userIdKey, token, userExpireTime, TimeUnit.MINUTES);
        redisCache.setCacheObject(userTokenKey, JSON.toJSONString(users), userExpireTime, TimeUnit.MINUTES);
        return token;
    }

    public String createToken(Manager manager) {
        Long managerId = manager.getManagerId();
        String token = jwtUtils.createToken(managerId);
        String managerTokenKey = getManagerKey(token);
        String managerIdKey = getManagerKey(managerId.toString());
        redisCache.setCacheObject(managerIdKey, token, managerExpireTime, TimeUnit.MINUTES);
        redisCache.setCacheObject(managerTokenKey, JSON.toJSONString(manager), managerExpireTime, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 通过token删除redis缓存: token和user对象
     * 1. 通过token拿到user对象, 并从中获取userId
     * 2. 通过token删除token-user缓存
     * 3. 通过userId删除userId-token缓存
     * @param token
     */
    public boolean removeUserToken(String token) {
        String userTokenKey = getUserKey(token);
        String userIdKey = getUserKey(getUserCache(token).getUserId().toString());
        return redisCache.deleteObject(userTokenKey) && redisCache.deleteObject(userIdKey);
    }

    /**
     * 通过userId删除redis缓存: token和user对象
     * 1. 通过userId拿到token
     * 2. 通过token删除token-user缓存
     * 3. 通过userId删除userId-token缓存
     * @param userId
     */
    public boolean removeUserToken(Long userId) {
        String userTokenKey = getUserKey(getUserTokenCache(userId));
        String userIdKey = getUserKey(userId.toString());
        return redisCache.deleteObject(userTokenKey) && redisCache.deleteObject(userIdKey);
    }

    /**
     * 通过token删除redis缓存: token和manager对象
     * 1. 通过token拿到manager对象, 并从中获取managerId
     * 2. 通过token删除token-manager缓存
     * 3. 通过managerId删除managerId-token缓存
     * @param token
     */
    public boolean removeManagerToken(String token) {
        String managerTokenKey = getManagerKey(token);
        String managerIdKey = getManagerKey(getManagerCache(token).getManagerId().toString());
        return redisCache.deleteObject(managerTokenKey) && redisCache.deleteObject(managerIdKey);
    }

    /**
     * 通过managerId删除redis缓存: token和manager对象
     * 1. 通过managerId拿到token
     * 2. 通过token删除token-manager缓存
     * 3. 通过managerId删除managerId-token缓存
     * @param managerId
     */
    public boolean removeManagerToken(Long managerId) {
        String managerTokenKey = getManagerKey(getManagerTokenCache(managerId));
        String managerIdKey = getManagerKey(managerId.toString());
        return redisCache.deleteObject(managerTokenKey) && redisCache.deleteObject(managerIdKey);
    }

    public<T> T getCache(String key) {
        return redisCache.getCacheObject(key);
    }

    /**
     * 通过token获取User对象
     * @param token 传入的token不需要取key
     * @return
     */
    public Users getUserCache(String token) {
        String userVoJson = redisCache.getCacheObject(getUserKey(token));
        Users users = JSON.parseObject(userVoJson, Users.class);
        return users;
    }

    /**
     * 通过userId获取token
     * @param userId 传入的id不需要取key
     * @return
     */
    public String getUserTokenCache(Long userId) {
        return redisCache.getCacheObject(getUserKey(userId.toString()));
    }

    /**
     * 通过token获取Manager对象
     * @param token 传入的token不需要取key
     * @return
     */
    public Manager getManagerCache(String token) {
        String managerVoJson = redisCache.getCacheObject(getManagerKey(token));
        Manager manager = JSON.parseObject(managerVoJson, Manager.class);
        return manager;
    }

    /**
     * 通过managerId获取token
     * @param managerId 传入的id不需要取key
     * @return
     */
    public String getManagerTokenCache(Long managerId) {
        return redisCache.getCacheObject(getManagerKey(managerId.toString()));
    }

    public void updateCache(String token, Users users) {
        redisCache.setCacheObject(getUserKey(token), JSON.toJSONString(users));
    }

    public void updateCache(String token, Manager manager) {
        redisCache.setCacheObject(getManagerKey(token), JSON.toJSONString(manager));
    }

    private String getUserKey(String s) {
        return getKey(USER_TAG, s);
    }

    private String getManagerKey(String s) {
        return getKey(MANAGER_TAG, s);
    }

    private String getKey(String pre, String s) {
        return pre + s;
    }


    public void removeToken(String key) {
        redisCache.deleteObject(key);
    }
}
