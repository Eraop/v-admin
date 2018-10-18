package com.eraop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.eraop.*.mapper")
public class VAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VAdminApplication.class, args);
    }
}
