package com.gotostudy.study.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
// 扫描引入其他模块的配置信息
@ComponentScan(basePackages = {"com.gotostudy"})
public class StudyEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyEduApplication.class, args);
    }

}
