package com.github.fhr.jsonrpc4j.multi.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fhr.jsonrpc4j.service.content.ContentService;
import com.github.fhr.jsonrpc4j.service.content.ContentServiceImpl;
import com.github.fhr.jsonrpc4j.service.user.User;
import com.github.fhr.jsonrpc4j.service.user.UserService;
import com.github.fhr.jsonrpc4j.service.user.UserServiceImpl;
import com.googlecode.jsonrpc4j.JsonRpcClient;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.googlecode.jsonrpc4j.StreamServer;

import java.io.IOException;
import java.net.*;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public class StreamMultiServiceClient {
    public static void main(String[] args) throws Throwable {
        JsonRpcClient rpcClient = new JsonRpcClient();
        Socket socket = new Socket();
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), 52420);
        socket.connect(serverAddress);

//        UserService userService = ProxyUtil.createClientProxy(
//                StreamMultiServiceClient.class.getClassLoader(),
//                UserService.class,
//                rpcClient);

//        User user = userService.createUser("bob", "the builder");

        System.out.println(rpcClient.invokeAndReadResponse("getContent", new Object[]{"bob"}, String.class, socket.getOutputStream(), socket.getInputStream()));
    }
}
