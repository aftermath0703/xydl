package com.flrjcx.xypt.common.config;

import com.flrjcx.xypt.common.handler.ApiInterceptor;
import com.flrjcx.xypt.common.handler.ValidationInterceptor;
import com.flrjcx.xypt.common.handler.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author malaka
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Resource
    private ValidationInterceptor userValidationInterceptor;

    @Resource
    private ApiInterceptor apiInterceptor;

    @Bean
    public FilterRegistrationBean filterRegist() {
        FilterRegistrationBean frBean = new FilterRegistrationBean();
        frBean.setFilter(new XssFilter());
        frBean.addUrlPatterns("/*");
        return frBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor)
                .addPathPatterns(
                        "/**"
                );
        registry.addInterceptor(userValidationInterceptor)
                .addPathPatterns(
                        "/**"
                );
    }


    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }

}

