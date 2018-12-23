package com.wanli.collect;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wanli.collect.dao")
public class CollectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectApplication.class, args);
    }

}

