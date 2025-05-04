package com.msb.servicevericationcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceVericationcodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceVericationcodeApplication.class, args);
    }

}
