package com.wupao.oneclickrelease;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wuxianglong
 */
@SpringBootApplication
@MapperScan(value = "com.wupao.oneclickrelease.mapper")
public class OneClickReleaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneClickReleaseApplication.class, args);
    }

}
