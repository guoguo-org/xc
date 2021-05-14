package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.domain.cms")//扫描实体类
@ComponentScan("com.api.cms")//扫描接口
@ComponentScan("com")//扫描所哟类
public class CmsManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsManagerApplication.class,args);
    }


}
