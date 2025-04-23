package com.msb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.msb.mapper")
public class serviceDriverUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(serviceDriverUserApplication.class, args);
    }
}
