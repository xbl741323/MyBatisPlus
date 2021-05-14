package com.xu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//扫描我们的mapper文件夹
//@MapperScan("com.xu.mapper")
@SpringBootApplication
public class StudyMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyMybatisPlusApplication.class, args);
    }

}
