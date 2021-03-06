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
运行程序后控制台输出日志如下：
```
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@7c447c76] was not registered for synchronization because synchronization is not active
2021-05-12 16:18:33.472  INFO 23164 --- [           main] com.zaxxer.hikari.HikariDataSource       : study_db - Starting...
2021-05-12 16:18:33.842  INFO 23164 --- [           main] com.zaxxer.hikari.HikariDataSource       : study_db - Start completed.
JDBC Connection [HikariProxyConnection@1512273713 wrapping com.mysql.cj.jdbc.ConnectionImpl@7d2998d8] will not be managed by Spring
==>  Preparing: SELECT id,name,age,email FROM user 
==> Parameters: 
<==    Columns: id, name, age, email
<==        Row: 1, Jone, 18, test1@baomidou.com
<==        Row: 2, Jack, 20, test2@baomidou.com
<==        Row: 3, Tom, 28, test3@baomidou.com
<==        Row: 4, Sandy, 21, test4@baomidou.com
<==        Row: 5, Billie, 24, test5@baomidou.com
<==      Total: 5
```
这个日志输出很方便开发者查询数据库操作信息，很好用！



### CRUD扩展
#### 一、INSERT(插入操作)

### 主键生成策略
分布式系统唯一id生成：https://www.cnblogs.com/haoxinyue/p/5208136.html

#### 雪花算法：
snowflake是Twitter开源的分布式ID生成算法，结果是一个long型的ID。其核心思想是：使用41bit作为毫秒数，10bit作为机器的ID（5个bit是数据中心，5个bit的机器ID），12bit作为毫秒内的流水号（意味着每个节点在每毫秒可以产生 4096 个 ID），最后还有一个符号位，永远是0。具体实现的代码可以参看https://github.com/twitter/snowflake
雪花算法支持的TPS可以达到419万左右（2^22*1000）。
雪花算法在工程实现上有单机版本和分布式版本。单机版本如下，分布式版本可以参看美团leaf算法：https://github.com/Meituan-Dianping/Leaf
可以保证几乎全球唯一了！

#### 主键自增：
1、实体类字段上 加上注解@TableId(type = IdType.AUTO)（type的值有多种选择）

2、数据库中的对应主键字段一定要是自增的！

3、测试插入数据即可！

#### 其它@TableId中type值源码解释
```
    AUTO(0), // 数据库id自增
    NONE(1), // 未设置主键
    INPUT(2), // 手动输入
    ASSIGN_ID(3),
    ASSIGN_UUID(4),
    /** @deprecated */
    @Deprecated
    ID_WORKER(3), // 默认的全局唯一id
    /** @deprecated */
    @Deprecated
    ID_WORKER_STR(3), // ID_WORKER 字符串表示法
    /** @deprecated */
    @Deprecated
    UUID(4); // 全局唯一id uuid
```

#### 二、UPDATE(更新操作)

```
//测试更新（update）
    @Test
    public void testUpdate(){
        User user = new User();
        // 可以通过条件自动拼接动态sql
        user.setId((long) 45);
        user.setName("e手动");
        user.setAge(18);
        // 注意：updateById 的参数是一个对象！
        int result = userMapper.updateById(user);
        System.out.println(result);
    }
```
日志输出如下：
```
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@f951a7f] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@447477005 wrapping com.mysql.cj.jdbc.ConnectionImpl@22a0d4ea] will not be managed by Spring
==>  Preparing: UPDATE user SET name=?, age=? WHERE id=? 
==> Parameters: e手动(String), 18(Integer), 45(Long)
<==    Updates: 1
```
所有的SQL都是自动帮你动态配置的！使用起来很方便！

#### 自动填充

创建时间、修改时间！这些操作一般都是自动化完成的，我们不希望手动更新！

阿里巴巴开发手册要求：所有的数据库表：gmt_create、gmt_modified几乎所有的表都要配置上！而且需要自动化！

#### 1、数据库级别（不建议这么操作）
直接在数据库中设置默认值和更新操作
#### 2、代码级别
1、添加对应时间字段（不要设置默认值和更新操作）
2、实体类字段属性上添加TableField注解
```
// 字段添加填充内容
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
```
3、编写处理器处理相应注解
```
package com.xu.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component //一定不要忘记把处理器加到IOC容器中！
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时的填充策略
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充......");
        // setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject)
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }

    // 更新时的填充策略
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充......");
        this.setFieldValByName("updateTime",new Date(),metaObject);
    }
}
```
4、测试插入、测试更新、观察时间即可！

#### 乐观锁
在面试过程中，我们经常会被问到乐观锁，悲观锁！这个其实很简单
```
乐观锁：顾名思义十分乐观，它总是认为不会出现问题，无论干什么都不去上锁！如果出现了问题，就再次更新值测试！
悲观锁：顾名思义十分悲观，它总是认为会出现问题，无论干什么都去上锁！再去操作！
```
这里我们主要讲解乐观锁机制！

##### 乐观锁实现方式：
+ 去除记录时，获取当前version
+ 更新时，带上这个version
+ 执行更新时，set version = newVersion where version = oldVersion
+ 如果version不对，就更新失败
```
乐观锁：1、先查询，获得版本号 version = 1
--A
update user set name = "xu",version = version + 1
where id = 2 and version  = 1

--B 线程抢先完成，这个时候 version = 2,会导致 A 修改失败！
update user set name = "xu",version = version + 1
where id = 2 and version  = 1
```

##### 测试一下MP的乐观锁插件
1. 给数据库中添加version字段
2. 对应实体类加上version字段并加上@Version注解
```
@Version //乐观锁的Version注解
    private Integer version;
```
3. 注册组件
```
package com.xu.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//扫描我们的mapper文件夹
@MapperScan("com.xu.mapper") // 测试乐观锁移到了这，原先在主启动器上面
@EnableTransactionManagement //开启事务
@Configuration //代表是一个配置类
public class MyBatisPlusConfig {

    // 注册乐观锁插件（新版，旧版已弃用）
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }
}
```
4. 测试乐观锁（在测试类中进行测试）
##### 测试乐观锁成功
```
 // 测试乐观锁成功
    @Test
    public void testLockSuccess(){

        //1、查询用户信息
        User user = userMapper.selectById(45);
        //2、修改用户信息
        user.setName("小白");
        user.setAge(10);
        //3、执行更新操作
        userMapper.updateById(user);
    }
```
测试结果如下：
```
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@508a65bf] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@401792389 wrapping com.mysql.cj.jdbc.ConnectionImpl@1894e40d] will not be managed by Spring
==>  Preparing: SELECT id,name,age,email,version,create_time,update_time FROM user WHERE id=?
==> Parameters: 45(Integer)
<==    Columns: id, name, age, email, version, create_time, update_time
<==        Row: 45, e手动, 18, 14s.com, 1, null, 2021-05-12 18:36:18
<==      Total: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@508a65bf]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6ede46f6] was not registered for synchronization because synchronization is not active
2021-05-13 09:56:58.611  INFO 18852 --- [           main] com.xu.handler.MyMetaObjectHandler       : 开始更新填充......
JDBC Connection [HikariProxyConnection@278986288 wrapping com.mysql.cj.jdbc.ConnectionImpl@1894e40d] will not be managed by Spring
==>  Preparing: UPDATE user SET name=?, age=?, email=?, version=?, update_time=? WHERE id=? AND version=?
==> Parameters: 小白(String), 10(Integer), 14s.com(String), 2(Integer), 2021-05-13 09:56:58.611(Timestamp), 45(Long), 1(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@6ede46f6]
```
##### 测试乐观锁失败
```
// 测试乐观锁失败,多线程如下
    @Test
    public void testLockFail(){
        // 线程1
        User user = userMapper.selectById(45);
        user.setName("小白122");
        user.setAge(10);

        // 模拟另外一个线程执行了插队操作
        User user2 = userMapper.selectById(45);
        user2.setName("小白22333");
        user2.setAge(10);
        userMapper.updateById(user2);
        
        // 可以用自旋锁来多次尝试提交
        userMapper.updateById(user); // 如果没有乐观锁就会覆盖插队线程的值
    }
```
测试结果如下：
```
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@216e0771] was not registered for synchronization because synchronization is not active
2021-05-13 10:10:36.073  INFO 15436 --- [           main] com.xu.handler.MyMetaObjectHandler       : 开始更新填充......
JDBC Connection [HikariProxyConnection@1905280105 wrapping com.mysql.cj.jdbc.ConnectionImpl@5cbe2654] will not be managed by Spring
==>  Preparing: UPDATE user SET name=?, age=?, email=?, version=?, update_time=? WHERE id=? AND version=?
==> Parameters: 小白22333(String), 10(Integer), 14s.com(String), 3(Integer), 2021-05-13 10:10:36.073(Timestamp), 45(Long), 2(Integer)
<==    Updates: 1
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@216e0771]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@fab35b1] was not registered for synchronization because synchronization is not active
2021-05-13 10:10:36.082  INFO 15436 --- [           main] com.xu.handler.MyMetaObjectHandler       : 开始更新填充......
JDBC Connection [HikariProxyConnection@1778994610 wrapping com.mysql.cj.jdbc.ConnectionImpl@5cbe2654] will not be managed by Spring
==>  Preparing: UPDATE user SET name=?, age=?, email=?, version=?, update_time=? WHERE id=? AND version=?
==> Parameters: 小白122(String), 10(Integer), 14s.com(String), 3(Integer), 2021-05-13 10:10:36.082(Timestamp), 45(Long), 2(Integer)
<==    Updates: 0 //注意这里并没有执行更新操作（乐观锁阻止了本次操作）
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@fab35b1]
```

#### 三、SELECT(查询操作)
```
    // 测试按照id查询
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(45);
        System.out.println(user);
    }

    // 测试批量查询
    @Test
    public void testSelectBatchId(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        System.out.println(users);
    }

    // 按条件查询之一使用map操作
    @Test
    public void testSelectBatchIds(){
        HashMap<String,Object> map = new HashMap<>();
        // 自定义查询
        map.put("name","小白22333");
        List<User> users = userMapper.selectByMap(map);
        System.out.println(users);
    }
```
#### 分页查询
分页网站使用的十分之多！
1. 原始的limit进行分页
2. pageHelper第三方插件
3. MP其实也内置了分页插件
```
如何使用？步骤如下：
1、配置分页插件
@Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor()); // 注册乐观锁插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2)); // 注册分页插件
        return interceptor;
    }
2、直接使用
// 测试分页查询
    @Test
    public void testPageSelect(){
        // current:当前页 size:页面大小
        Page<User> page = new Page<>(1,10);
        userMapper.selectPage(page,null);
        System.out.println(page.getRecords());
    }
```
测试结果如下：
```
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2776015d] was not registered for synchronization because synchronization is not active
2021-05-13 11:06:49.752  INFO 7840 --- [           main] com.zaxxer.hikari.HikariDataSource       : study_db - Starting...
2021-05-13 11:06:49.962  INFO 7840 --- [           main] com.zaxxer.hikari.HikariDataSource       : study_db - Start completed.
JDBC Connection [HikariProxyConnection@1407986024 wrapping com.mysql.cj.jdbc.ConnectionImpl@58ff8d79] will not be managed by Spring
==>  Preparing: SELECT COUNT(*) FROM user
==> Parameters: 
<==    Columns: COUNT(*)
<==        Row: 15
<==      Total: 1
==>  Preparing: SELECT id,name,age,email,version,create_time,update_time FROM user LIMIT ?
==> Parameters: 10(Long)
<==    Columns: id, name, age, email, version, create_time, update_time
<==        Row: 1, Jone, 18, test1@baomidou.com, 1, null, null
<==        Row: 2, Jack, 20, test2@baomidou.com, 1, null, null
<==        Row: 3, Tom, 28, test3@baomidou.com, 1, null, null
<==        Row: 4, Sandy, 21, test4@baomidou.com, 1, null, null
<==        Row: 5, Billie, 24, test5@baomidou.com, 1, null, null
<==        Row: 12, 2, 23, 123@323.com, 1, null, null
<==        Row: 23, e, 23, 121@12.com, 1, null, null
<==        Row: 42, e, 24, 14s.com, 1, null, null
<==        Row: 45, 小白22333, 10, 14s.com, 3, null, 2021-05-13 10:10:36
<==        Row: 46, e, 14, 14s.com, 1, null, null
<==      Total: 10
```

#### 四、DELETE(删除操作)
```
// 测试删除
    @Test
    public void testDelete(){
        userMapper.deleteById(2); // 根据 id 删除单个数据
        userMapper.deleteBatchIds(Arrays.asList(1,3)); //根据 id 删除多个数据

        HashMap<String,Object> map = new HashMap<>();
        map.put("name","2");
        userMapper.deleteByMap(map); // 按条件删除数据
    }
```

工作中，我们会遇到一些问题，逻辑删除！
#### 逻辑删除
```
物理删除：从数据库中直接移除
逻辑删除：在数据库中没有被移除，而是通过一个变量来让它失效！deleted=0 => deleted=1
```
管理员可以查看被删除的记录，防止数据的丢失，相当于回收站！

#### 测试如下：
1. 在数据库中添加deleted字段 默认值设置为0
2. 在实体类中添加deleted字段，并添加@TableLogic注解
```
 @TableLogic // 逻辑删除注解
    private Integer deleted;
```
3. 配置（@Bean相关配置高版本后已经不需要了）,application.yml中配置如下
```
mybatis-plus:
  global-config:
    db-config:
      logic-delete-field: flag  # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
      logic-delete-value: 1 # 逻辑已删除值(默认为 1)
      logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
```
4. 测试一下删除！
```
 // 测试删除
    @Test
    public void testDelete(){
        userMapper.deleteById(4); //根据id删除单个数据
        userMapper.deleteBatchIds(Arrays.asList(1,3)); //根据id删除多个数据

        HashMap<String,Object> map = new HashMap<>();
        map.put("name","e3");
        userMapper.deleteByMap(map); // 按条件删除数据
    }
```
输出日志如下：
```
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5b275174] was not registered for synchronization because synchronization is not active
2021-05-13 14:38:14.714  INFO 20120 --- [           main] com.zaxxer.hikari.HikariDataSource       : study_db - Starting...
2021-05-13 14:38:14.955  INFO 20120 --- [           main] com.zaxxer.hikari.HikariDataSource       : study_db - Start completed.
JDBC Connection [HikariProxyConnection@1825903149 wrapping com.mysql.cj.jdbc.ConnectionImpl@2c7a8af2] will not be managed by Spring
==>  Preparing: UPDATE user SET deleted=1 WHERE id=? AND deleted=0
==> Parameters: 4(Integer)
<==    Updates: 1 // 逻辑删除本质进行的是更新操作
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@5b275174]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b2f5fcf] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@1687702287 wrapping com.mysql.cj.jdbc.ConnectionImpl@2c7a8af2] will not be managed by Spring
==>  Preparing: UPDATE user SET deleted=1 WHERE id IN ( ? , ? ) AND deleted=0
==> Parameters: 1(Integer), 3(Integer)
<==    Updates: 0 // 逻辑删除本质进行的是更新操作
Closing non transactional SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@2b2f5fcf]
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@53ec2968] was not registered for synchronization because synchronization is not active
JDBC Connection [HikariProxyConnection@512407823 wrapping com.mysql.cj.jdbc.ConnectionImpl@2c7a8af2] will not be managed by Spring
==>  Preparing: UPDATE user SET deleted=1 WHERE name = ? AND deleted=0
==> Parameters: e3(String)
<==    Updates: 1 // 逻辑删除本质进行的是更新操作
```
之后查询被逻辑删除的数据
```
 // 测试查询
    @Test
    public void testSelectById(){
        User user = userMapper.selectById(4);
        System.out.println(user);
    }
```
输出日志如下：
```
Creating a new SqlSession
SqlSession [org.apache.ibatis.session.defaults.DefaultSqlSession@61dde151] was not registered for synchronization because synchronization is not active
2021-05-13 14:44:10.735  INFO 25160 --- [           main] com.zaxxer.hikari.HikariDataSource       : study_db - Starting...
2021-05-13 14:44:11.005  INFO 25160 --- [           main] com.zaxxer.hikari.HikariDataSource       : study_db - Start completed.
JDBC Connection [HikariProxyConnection@278536229 wrapping com.mysql.cj.jdbc.ConnectionImpl@2241f05b] will not be managed by Spring
==>  Preparing: SELECT id,name,age,email,version,deleted,create_time,update_time FROM user WHERE id=? AND deleted=0
==> Parameters: 4(Integer)
<==      Total: 0  // 没有查询到结果
```

### 以上所有的CRUD及其扩展操作，我们都必须精通掌握，会大大提高工作效率！

### 条件构造器
十分重要：Wrapper 我们写一些复杂的sql可以用它来替代！
1. 测试1
```
@Test
    public void wrapperSelect(){
        // 查询name不为空，邮箱不为空，年龄大于等于12的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
               .isNotNull("email")
               .ge("age",12);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }
```
2. 测试2
```
 @Test
    public void wrapperSelectByName(){
        // 根据name查询一条用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","小白22333");
        User user = userMapper.selectOne(wrapper);
        System.out.println(user);
    }
```
3. 测试3
```
  @Test
    public void wrapperSelectFilter(){
        // 查询年龄在10到25之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",10,25);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);

    }
```
4. 测试4
```
 @Test
    public void selectBySql(){
        // 通过sql语句查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("age","select age from user where age < 30");
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);
    }
```

### 代码自动生成器（待补充）


