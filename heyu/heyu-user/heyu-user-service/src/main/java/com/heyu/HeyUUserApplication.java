package com.heyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;


@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.heyu.user.mapper")
public class HeyUUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeyUUserApplication.class, args);
    }
}
