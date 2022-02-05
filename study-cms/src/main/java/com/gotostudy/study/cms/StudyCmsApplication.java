package com.gotostudy.study.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.gotostudy"})
public class StudyCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyCmsApplication.class, args);
    }

}
