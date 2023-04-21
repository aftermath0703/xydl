package com.flrjcx.xypt.common.enums;

import java.util.concurrent.TimeUnit;

/**
 * @author malaka
 * @Description 缓存枚举
 * @date 2022/10/25 20:40
 */
public enum CacheTokenEnum {

    // 邮箱缓存
    CACHE_EMAIL("CACHE_EMAIL:", 30, TimeUnit.MINUTES);

    private String key;
    private Long cacheTime;
    private TimeUnit timeUnit;

    private CacheTokenEnum() {
    }

    private CacheTokenEnum(String key, long cacheTime, TimeUnit timeUnit) {
        this.key = key;
        this.cacheTime = cacheTime;
        this.timeUnit = timeUnit;
    }

    public String getKey() {

        return key;
    }

    public Long getCacheTime() {
        return cacheTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }
}
