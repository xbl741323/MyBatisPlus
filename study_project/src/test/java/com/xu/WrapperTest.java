package com.xu;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.mapper.UserMapper;
import com.xu.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class WrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {

    }

//    @Test
    public void wrapperSelect(){
        // 查询name不为空，邮箱不为空，年龄大于等于12的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("name")
               .isNotNull("email")
               .ge("age",12);
        List<User> users = userMapper.selectList(wrapper); // 查询多条数据
        System.out.println(users);
    }

//    @Test
    public void wrapperSelectByName(){
        // 根据name查询一条用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","小白22333");
        User user = userMapper.selectOne(wrapper); // 查询一条数据
        System.out.println(user);
    }

//    @Test
    public void wrapperSelectFilter(){
        // 查询年龄在10到25之间的用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.between("age",10,25);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);

    }

    @Test
    public void selectBySql(){
        // 通过sql语句查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.inSql("age","select age from user where age < 30");
//        List<Object> objects = userMapper.selectObjs(wrapper);
        List<User> users = userMapper.selectList(wrapper);
        System.out.println(users);

    }

}
