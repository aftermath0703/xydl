package com.flrjcx.xypt.common.annotation;

import com.flrjcx.xypt.common.enums.operate.OperateTypeEnum;
import com.flrjcx.xypt.common.enums.operate.OperatorTypeEnum;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * @author aftermath
 * @date 2022-12-02 14:06:39
 */
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OperationLog {
    /**
     * 模块
     * 命名规范统一 user/manager/common:模块名
     */
    public String title() default "";

    /**
     * 操作类别
     */
    public OperateTypeEnum operateType() default OperateTypeEnum.OTHER;

    /**
     * 操作人类别
     */
    public OperatorTypeEnum operatorType() default OperatorTypeEnum.MANAGE;

    /**
     * 是否保存请求的参数
     */
    public boolean isSaveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    public boolean isSaveResponseData() default true;
}
