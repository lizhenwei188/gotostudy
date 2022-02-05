package com.gotostudy.study.usr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = {"com.gotostudy"})
public class StudyUsrApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyUsrApplication.class, args);
    }

}
