package com.eraop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动
 * SpringBootApplication注解中已经包含了@ComponentScan和@EnableAutoConfiguration注解
 * EnableAutoConfiguration注解最终使ConfigurationPropertiesAutoConfiguration类上的@EnableAutoConfiguration生效
 *
 * @author weiyi
 */
@SpringBootApplication
@MapperScan(basePackages = "com.eraop.*.mapper")
public class VAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VAdminApplication.class, args);
    }
}
