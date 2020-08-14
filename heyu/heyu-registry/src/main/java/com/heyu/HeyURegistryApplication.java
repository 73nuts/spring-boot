package com.heyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
/*
eureka注册中心引导类
 */
public class HeyURegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeyURegistryApplication.class, args);
    }
}
