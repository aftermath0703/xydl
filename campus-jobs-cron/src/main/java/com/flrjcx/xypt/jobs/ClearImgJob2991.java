package com.flrjcx.xypt.jobs;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 定时任务
 *
 * @author Flrjcx
 */
@SpringBootApplication
//@EnableProcessApplication
@EnableAsync
@EnableTransactionManagement
@Slf4j
@ComponentScan(basePackages = { "com.flrjcx.xypt.jobs.*" })
@MapperScan(basePackages = "com.flrjcx.xypt.jobs.mapper")
@EnableFeignClients(basePackages = "com.flrjcx.xypt.client",defaultConfiguration = FeignClientProperties.FeignClientConfiguration.class)
@EnableDiscoveryClient
@EnableScheduling
public class ClearImgJob2991 implements CommandLineRunner {
    public static void main(String[] args){
        SpringApplication.run(ClearImgJob2991.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("[---------- SpringBoot Successful.2991 ----------]");
    }
}
