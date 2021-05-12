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
#### 1、SQL谁帮我们写的？
答：MyBatisPlus
#### 2、方法哪里来的？
答：MyBatisPlus 都写好了

### 特性
#### 1、无侵入
只做增强不做改变，引入它不会对现有工程产生影响，如丝般顺滑

#### 2、损耗小
启动即会自动注入CURD，性能基本无损耗，直接面向对象操作，BaseMapper

#### 3、强大的CRUD操作
内置通用Mapper、通用Service，仅仅通过少量配置便可实现单表大部分CRUD操作，更有强大的条件构造器，满足各类使用需求，简单的CRUD操作，可以直接用它

#### 4、支持Lambda形式调用
通过Lambda表达式，方便的编写各类查询条件，无需担心字段写错

#### 5、支持主键自动生成
支持多达4种主键策略（内含分布式唯一ID生成器-Sequence），可自由配置，完美解决主键问题

#### 6、支持ActiveRecord模式
支持ActiveRecord形式调用，实体类只需继承Model类即可进行强大的CRUD操作

#### 7、支持自定义全局通用操作
支持全局通用方法注入（Write once,use anywhere）

#### 8、内置代码生成器
采用代码或者Maven插件可快速生成Mapper、Model、Service、Controller层代码，支持模板引擎，更有超多自定义配置等你来使用（自动帮你生成代码）

#### 9、内置分页插件
基于MyBatis物理分页，开发者无须担心具体操作，配置好插件后，写分页等同于普通List查询

#### 10、分页插件支持多种数据库
支持MySQL、MariaDB、Oracle、DB2、H2、HSQL、SQLite、Postgre、SQLServer等多种婆数据库

#### 11、内置性能分析插件
可输出sql语句以及其执行时间，建议开发测试启用该功能

#### 12、内置全局拦截插件
提供全表delete、update等操作智能分析阻断，也可以自定义拦截规则，预防误操作

### 配置日志
在application.yml文件中加入以下代码：（log-impl后面的值可用得有很多，这里只用了控制台输出）
```
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
```
