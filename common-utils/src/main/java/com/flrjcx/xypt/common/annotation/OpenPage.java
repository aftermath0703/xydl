package com.flrjcx.xypt.common.annotation;

import java.lang.annotation.*;

/**
 * @author malaka
 * @Description 分页注解
 * @date 2022/10/21 14:43
 */
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenPage {

}