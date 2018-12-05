package com.fhr.dubbo.quickstart.simple.provider;

import com.fhr.dubbo.quickstart.simple.IUserService;

/**
 * @description:
 * @author:
 * @create: 2018-03-25 00:25
 **/
public class UserServiceClass implements IUserService {

    public String sayHello(String name) {
        return "hello " + name;
    }
}
