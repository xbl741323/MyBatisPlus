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

    //测试插入（insert）
//    @Test
//    public void testInsert(){
//        User user = new User();
//        user.setName("e3");
//        user.setAge(13);
//        user.setEmail("14s.com");
//        int result = userMapper.insert(user);
//        System.out.println(result);
//    }

    //测试更新（update）
    @Test
    public void testUpdate(){
        User user = new User();
        // 通过条件自动拼接动态sql
        user.setId((long) 45);
        user.setName("e手动");
        user.setAge(18);
        // 注意：updateById 的参数是一个对象！
        int result = userMapper.updateById(user);
        System.out.println(result);
    }

}
