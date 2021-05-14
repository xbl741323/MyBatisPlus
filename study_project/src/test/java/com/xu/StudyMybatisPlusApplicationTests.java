package com.xu;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xu.mapper.UserMapper;
import com.xu.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
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
//        List<User> users = userMapper.selectList(null);
//        users.forEach(System.out::println);
    }

    //测试插入（insert）
//    @Test
    public void testInsert(){
        User user = new User();
        user.setName("e3");
        user.setAge(13);
        user.setEmail("14s.com");
        int result = userMapper.insert(user);
        System.out.println(result);
    }

    //测试更新（update）
//    @Test
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

    // 测试乐观锁成功
//    @Test
    public void testLockSuccess(){

        //1、查询用户信息
        User user = userMapper.selectById(45);
        //2、修改用户信息
        user.setName("小白");
        user.setAge(10);
        //3、执行更新操作
        userMapper.updateById(user);
    }

    // 测试乐观锁失败,多线程如下
//    @Test
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

    // 测试查询
//    @Test
    public void testSelectById(){
        User user = userMapper.selectById(4);
        System.out.println(user);
    }

    // 测试批量查询
//    @Test
    public void testSelectBatchId(){
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1,2,3));
        System.out.println(users);
    }

    // 按条件查询之一使用map操作
//    @Test
    public void testSelectBatchIds(){
        HashMap<String,Object> map = new HashMap<>();
        // 自定义查询
        map.put("name","小白22333");
        List<User> users = userMapper.selectByMap(map);
        System.out.println(users);
    }

    // 测试分页查询
//    @Test
    public void testPageSelect(){
        // current:当前页 size:页面大小
        Page<User> page = new Page<>(1,10);
        userMapper.selectPage(page,null);
        System.out.println(page.getRecords());
    }

    // 测试删除
//    @Test
    public void testDelete(){
        userMapper.deleteById(4); //根据id删除单个数据
        userMapper.deleteBatchIds(Arrays.asList(1,3)); //根据id删除多个数据

        HashMap<String,Object> map = new HashMap<>();
        map.put("name","e3");
        userMapper.deleteByMap(map); // 按条件删除数据
    }
}
