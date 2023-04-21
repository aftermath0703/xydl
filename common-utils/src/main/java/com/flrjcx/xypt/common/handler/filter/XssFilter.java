package com.flrjcx.xypt.common.handler.filter;

import com.flrjcx.xypt.common.handler.request.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author malaka
 * @Description TODO
 * @date 2022/11/5 16:37
 */
public class XssFilter implements Filter {

    //配置接口过滤
    private  final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList(
                    "/xypt/api/common/fileUploader/uploadPic",
                    "/xypt/api/common/fileUploader/updateAvatar"
            )));

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            HttpServletRequest servletRequest = (HttpServletRequest) request;
            String requestURI = servletRequest.getRequestURI();
            if (ALLOWED_PATHS.contains(requestURI)){
                chain.doFilter(servletRequest,response);
            }else {
                requestWrapper = new XssHttpServletRequestWrapper(servletRequest);
                //获取请求中的流如何，将取出来的字符串，再次转换成流，然后把它放入到新request对象中。
                // 在chain.doFiler方法中传递新的request对象
                chain.doFilter(requestWrapper, response);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
