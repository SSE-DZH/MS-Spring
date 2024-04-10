package com.zhiend.student_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching//开启缓存注解功能
public class StudentServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServerApplication.class, args);
    }

}
