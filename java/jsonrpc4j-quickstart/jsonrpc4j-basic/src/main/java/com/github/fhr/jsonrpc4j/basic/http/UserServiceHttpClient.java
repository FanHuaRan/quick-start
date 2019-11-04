package com.github.fhr.jsonrpc4j.basic.http;

import com.github.fhr.jsonrpc4j.service.user.User;
import com.github.fhr.jsonrpc4j.service.user.UserService;
import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.googlecode.jsonrpc4j.ProxyUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public class UserServiceHttpClient {
    public static void main(String[] args) throws MalformedURLException {
        // create JsonRpcHttpClient
        JsonRpcHttpClient client = new JsonRpcHttpClient(
                new URL("http://example.com/UserService.json"));

        // create client proxy with client
        UserService userService = ProxyUtil.createClientProxy(
                UserServiceHttpClient.class.getClassLoader(),
                UserService.class,
                client);

        // do invoke
        User user = userService.createUser("bob", "the builder");
    }
}
