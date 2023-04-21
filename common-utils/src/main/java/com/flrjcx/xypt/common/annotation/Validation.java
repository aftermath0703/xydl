package com.flrjcx.xypt.common.annotation;

import java.lang.annotation.*;

/**
 * @author malaka
 * 用户登录认证注解
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validation {
}
