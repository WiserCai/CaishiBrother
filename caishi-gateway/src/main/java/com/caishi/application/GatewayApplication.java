package com.caishi.application;

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
public class GatewayApplication {

    public static void main(String[] args) {

        SpringApplication.run(GatewayApplication.class,args);

    }

}

















