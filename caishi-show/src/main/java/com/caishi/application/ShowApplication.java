package com.caishi.application;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author CYC
 * @Version 1.0.0
 * @Description
 **/
@SpringBootApplication(scanBasePackages = "com.caishi")
@EnableDiscoveryClient
@MapperScan("com.caishi.mapper")
public class ShowApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShowApplication.class,args);

    }

}

