package com.flrjcx.xypt;

import com.flrjcx.xypt.kafka.FeignClientConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author Flrjcx
 */
@Slf4j
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.flrjcx.xypt.*" })
@MapperScan(basePackages = "com.flrjcx.xypt.mapper")
@EnableFeignClients(basePackages = "com.flrjcx.xypt.client",defaultConfiguration = FeignClientConfiguration.class)
@EnableDiscoveryClient
public class XyptProvider2995 implements CommandLineRunner {
    public static void main(String[] args){
        SpringApplication.run(XyptProvider2995.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("[---------- SpringBoot Successful.2995 ----------]");
    }
}
