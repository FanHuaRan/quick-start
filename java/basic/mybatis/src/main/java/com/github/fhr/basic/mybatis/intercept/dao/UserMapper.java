package com.github.fhr.basic.mybatis.intercept.dao;

import com.github.fhr.basic.mybatis.intercept.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Fan Huaran
 * created on 2019/2/20
 * @description
 */
public interface UserMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id") //回写自增的主键ID
    @Insert("insert into users (username,password,status)values(#{username},#{password},#{status})")
    Integer addUser(User user);

    @Delete("delete from users where id=#{0}")
    Integer deleteUserById(Integer id);

    @Update("update users set username=#{username},password=#{password},status=#{status} where id=#{id}")
    Integer updateUser(User user);

    @Select("select * from users where id=#{0}")
    User getById(Integer id);

    @Select("select * from users")
    List<User> queryUserList();
}
