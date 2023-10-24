package com.app.health_log;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan(value = "com.app.health_log", annotationClass = Mapper.class)
@SpringBootApplication
public class health_logApplication {

    public static void main(String[] args) {
        SpringApplication.run(health_logApplication.class, args);
    }

}
