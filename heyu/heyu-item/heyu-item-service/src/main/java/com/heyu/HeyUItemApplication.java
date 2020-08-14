package com.heyu;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;
/*
引导类
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.heyu.item.mapper")
public class HeyUItemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeyUItemApplication.class, args);
    }

}
