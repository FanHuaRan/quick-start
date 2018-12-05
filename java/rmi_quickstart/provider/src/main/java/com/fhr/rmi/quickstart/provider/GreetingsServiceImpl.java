package com.fhr.rmi.quickstart.provider;

import com.fhr.rmi.quickstart.api.GreetingsService;

/**
 * @author Fan Huaran
 * created on 2018/12/5
 * @description
 */
public class GreetingsServiceImpl implements GreetingsService {

    @Override
    public String sayHi(String name) {
        return "hi, " + name; // #2
    }
}
