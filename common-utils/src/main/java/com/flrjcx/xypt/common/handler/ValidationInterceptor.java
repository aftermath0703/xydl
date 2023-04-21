package com.flrjcx.xypt.common.handler;

import com.flrjcx.xypt.common.annotation.Validation;
import com.flrjcx.xypt.common.exception.ValidationException;
import com.flrjcx.xypt.common.model.param.common.Manager;
import com.flrjcx.xypt.common.model.param.common.Users;
import com.flrjcx.xypt.common.utils.ManagerThreadLocal;
import com.flrjcx.xypt.common.utils.TokenService;
import com.flrjcx.xypt.common.utils.UserThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author malaka
 * 用户登录验证
 */
@Component
public class ValidationInterceptor implements HandlerInterceptor {

    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行非Controller中的handler
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 验证用户
        // 获取带了注解 Validation 的 handler
        Validation annotation = method.getAnnotation(Validation.class);
        // 有，说明需要用户验证
        if (annotation != null) {
            // 获取请求头的token
            String token = request.getHeader(tokenService.getHeader());
            String uri = request.getRequestURI();
            if(uri.indexOf("/api/backend") > -1) {
                //indexOf 比 contains 效率高
                if (!managerValidation(token)) {
                    throw new ValidationException();
                }
            } else if(uri.indexOf("/api/common") > -1) {
                if (!userValidation(token) && !managerValidation(token)) {
                    throw new ValidationException();
                }
            } else {
                if (!userValidation(token)) {
                    throw new ValidationException();
                }
            }
        }
        // 没有，不需要用户验证
        return true;
    }

    public boolean managerValidation(String token) {
        Manager manager = tokenService.getManagerCache(token);
        if (Objects.isNull(manager)) {
            return false;
        }
        ManagerThreadLocal.put(manager);
        return true;
    }

    public boolean userValidation(String token) {
        Users user = tokenService.getUserCache(token);
        if (Objects.isNull(user)) {
            return false;
        }
        UserThreadLocal.put(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 如果不删除 ThreadLocal 有内存泄露的风险
        UserThreadLocal.remove();
    }
}
