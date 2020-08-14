package com.heyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/*
网关引导类
 */
@SpringBootApplication
@EnableZuulProxy
public class HeyUGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeyUGatewayApplication.class, args);
    }
}
