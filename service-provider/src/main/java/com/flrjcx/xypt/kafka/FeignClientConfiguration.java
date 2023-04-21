package com.flrjcx.xypt.kafka;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feign配置类
 *
 * @author Flrjcx
 */
@Configuration
public class FeignClientConfiguration {
    @Bean
    public Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }
}
