package com.flrjcx.xypt.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * gateway网关
 *
 * @author Flrjcx
 */
@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
public class ServerGatewayApp3000 implements CommandLineRunner {
    public static void main(String[] args){
        SpringApplication.run(ServerGatewayApp3000.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("[---------- SpringBoot Successful.3000 ----------]");
    }
}
