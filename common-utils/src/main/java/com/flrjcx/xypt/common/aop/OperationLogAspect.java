package com.flrjcx.xypt.common.aop;

import com.alibaba.fastjson.JSON;
import com.flrjcx.xypt.common.annotation.OperationLog;
import com.flrjcx.xypt.common.enums.KafkaTopicEnum;
import com.flrjcx.xypt.common.enums.ResultCodeEnum;
import com.flrjcx.xypt.common.enums.operate.OperateStatusEnum;
import com.flrjcx.xypt.common.enums.operate.OperatorTypeEnum;
import com.flrjcx.xypt.common.exception.WebServiceEnumException;
import com.flrjcx.xypt.common.model.domain.log.OperLog;
import com.flrjcx.xypt.common.model.param.common.Manager;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;

/**
 * 操作日志aop
 * @author aftermath
 * @date 2022-12-02 15:08:21
 */
@Aspect
@Component
@Slf4j
public class OperationLogAspect {
    @Resource
    private KafkaUtils kafkaUtils;


    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "@annotation(log)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, OperationLog log, Object jsonResult) {
        handleLog(joinPoint, log, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "@annotation(operLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, OperationLog operLog, Exception e) {
        handleLog(joinPoint, operLog, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, OperationLog operationLog, final Exception e, Object jsonResult) {
        try {
            Class<? extends Annotation> aClass = operationLog.annotationType();
            System.out.println(aClass);
            // 获取当前的用户
            Object currentUser;
            boolean isManager = operationLog.operatorType().equals(OperatorTypeEnum.MANAGE);
            if (isManager) {
                currentUser = ManagerThreadLocal.get();
            } else {
                currentUser = UserThreadLocal.get();
            }

            if (currentUser==null) {
                throw new Exception("获取用户信息失败");
            }

            // *========数据库日志=========*//
            OperLog operLog = new OperLog();
            operLog.setStatus((e!=null) ? OperateStatusEnum.FAIL.ordinal() : OperateStatusEnum.SUCCESS.ordinal());
            // 请求的地址
            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            operLog.setOperIp(ip);
            operLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
            operLog.setOperatorName(isManager ? ((Manager)currentUser).getNickName() : ((Users)currentUser).getNickName());

            if (e != null) {
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            getControllerMethodDescription(joinPoint, operationLog, operLog, jsonResult);
            // 保存数据库
            String operJsonStr = JSON.toJSONString(operLog);
            System.out.println(operJsonStr);
            kafkaUtils.sendMessageAsync(KafkaTopicEnum.TOPIC_OPER_LOG_SEND_MESSAGE, operJsonStr);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param operationLog     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, OperationLog operationLog, OperLog operLog, Object jsonResult) throws Exception {
        // 设置action动作
        operLog.setOperateType(operationLog.operateType().ordinal());
        // 设置标题
        operLog.setTitle(operationLog.title());
        // 设置操作人类别
        operLog.setOperatorType(operationLog.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (operationLog.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, operLog);
        }
        // 是否需要保存response，参数和值
        if (operationLog.isSaveResponseData() && jsonResult!=null) {
            operLog.setJsonResult(StringUtils.substring(JSON.toJSONString(jsonResult), 0, 2000));
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     */
    private void setRequestValue(JoinPoint joinPoint, OperLog operLog) {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (o!=null && !isFilterObject(o)) {
                    try {
                        Object jsonObj = JSON.toJSON(o);
                        params.append(jsonObj.toString()).append(" ");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        } else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        } else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse
                || o instanceof BindingResult;
    }
}
