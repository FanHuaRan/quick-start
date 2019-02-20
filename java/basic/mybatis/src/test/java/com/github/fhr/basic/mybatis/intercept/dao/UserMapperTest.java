package com.github.fhr.basic.mybatis.intercept.dao;

import com.github.fhr.basic.mybatis.intercept.App;
import com.github.fhr.basic.mybatis.intercept.MybatisScanConfiguration;
import com.github.fhr.basic.mybatis.intercept.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;
import java.util.List;

/**
 * @author Fan Huaran
 * created on 2019/2/20
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DataSourceAutoConfiguration.class, MybatisAutoConfiguration.class, MybatisScanConfiguration.class})
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testAddUser() {
        User user = new User("admin", "123", 1);
        userMapper.addUser(user);
        Assert.assertNotNull(user.getId());
    }

    @Test
    public void testDeleteUserById() {
        User user = new User("admin", "123", 1);
        userMapper.addUser(user);
        Integer count = userMapper.deleteUserById(user.getId());
        Assert.assertEquals(new Integer(1), count);
    }

    @Test
    public void testUpdateUser() {
        User user = new User("admin", "123", 1);
        userMapper.addUser(user);
        user.setUsername("hr");
        userMapper.updateUser(user);
        Assert.assertEquals("hr", user.getUsername());
    }

    @Test
    public void testGetById() {
        User user = new User("admin", "123", 1);
        userMapper.addUser(user);

        User obj = userMapper.getById(user.getId());
        Assert.assertEquals("admin", obj.getUsername());
        Assert.assertEquals("123", obj.getPassword());
        Assert.assertEquals(new Integer(1), obj.getStatus());
    }

    @Test
    public void testQueryUserList() {
        User user = new User("admin", "123", 1);
        userMapper.addUser(user);
        List<User> users = userMapper.queryUserList();
        Assert.assertTrue(!users.isEmpty());
    }

}
