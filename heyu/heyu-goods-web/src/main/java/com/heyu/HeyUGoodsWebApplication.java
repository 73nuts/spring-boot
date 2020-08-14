package com.heyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/*
商品详情页引导类
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class HeyUGoodsWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeyUGoodsWebApplication.class, args);
    }
}
