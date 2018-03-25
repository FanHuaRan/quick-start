package com.fhr.dubbohelloworld.service;

/**
 * @description:
 * @author:
 * @create: 2018-03-25 00:25
 **/
public class UserServiceClass implements  IUserService {

    public String sayHello(String name) {
        return "hello " + name;
    }
}
