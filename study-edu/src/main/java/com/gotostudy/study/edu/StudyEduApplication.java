package com.gotostudy.study.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan(basePackages = {"com.gotostudy"})
public class StudyEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyEduApplication.class, args);
    }

}
