package com.github.fhr.jsonrpc4j.service.user;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public interface UserService {
    User createUser(String userName, String firstName, String password);
    User createUser(String userName, String password);
    User findUserByUserName(String userName);
    int getUserCount();
}
