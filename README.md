# MyBatisPlus
MyBatisPlus学习记录

### 注意
MyBatis 和 MyBatisPlus 不能同时存在

### 操作步骤如下
1、新建一个SpringBoot项目
在 pom.xml 文件中加入如下代码：
```
         <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.3.2</version>
        </dependency>
```
2、在application.yml文件中加入如下代码：
```
spring:
  datasource:
    name: study_db （自己对应的数据库名称）
    url: jdbc:mysql://localhost:3306/study_db?useUnicode=true&useSSL=FALSE&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: root
    password: ****** （自己对应的数据库密码）
    driver-class-name: com.mysql.cj.jdbc.Driver

server:
  port: 8888
  
```
3、建立实体类(要和数据库中类型字段保持一致)
```
package com.xu.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String email;

}
```
4、新建mapper文件夹，建对应的mapper文件（文件类型是接口文件），在对应的Mapper上面继承基本的接口BaseMapper
```
package com.xu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xu.pojo.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

//在对应的Mapper上面继承基本的接口BaseMapper
@Repository //代表持久层
public interface UserMapper extends BaseMapper<User> {
    //所有的crud操作都已经编写完成了
    //你不需要像以前一样配置一大堆文件了
}
```
5、主启动类上面加上@MapperScan("com.xu.mapper") 里面是对应的mapper文件路径
```
package com.xu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//扫描我们的mapper文件夹
@MapperScan("com.xu.mapper")
@SpringBootApplication
public class StudyMybatisPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyMybatisPlusApplication.class, args);
    }

}
```
以上步骤完成之后，就可以测试了
```
package com.xu;

import com.xu.mapper.UserMapper;
import com.xu.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudyMybatisPlusApplicationTests {

    //继承了BaseMapper,所有的方法都来自父类，我们也可以编写自己的扩展方法！
    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        //参数是一个Wrapper,条件构造器，这里我们先不用,初始化为null

        //查询全部用户
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

}
```
### 思考问题
1、SQL谁帮我们写的？
答：MyBatisPlus
2、方法哪里来的？
答：MyBatisPlus 都写好了
