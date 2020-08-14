package com.heyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HeyUUploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeyUUploadApplication.class, args);
    }
}
