package com.github.fhr.jsonrpc4j.service.user;

import com.github.fhr.jsonrpc4j.service.user.User;
import com.github.fhr.jsonrpc4j.service.user.UserService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public class UserServiceImpl implements UserService {
    private final Map<String, User> userDb = new ConcurrentHashMap<>();

    @Override
    public User createUser(String userName, String firstName, String password) {
        User user = new User();
        user.setUserName(userName);
        user.setFirstName(firstName);
        user.setPassword(password);
        userDb.put(userName, user);
        return user;
    }

    @Override
    public User createUser(String userName, String password) {
        return this.createUser(userName,null,password);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userDb.get(userName);
    }

    @Override
    public int getUserCount() {
        return userDb.size();
    }
}
