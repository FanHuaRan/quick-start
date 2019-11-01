package com.github.fhr.jsonrpc4j.multi.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fhr.jsonrpc4j.service.content.ContentService;
import com.github.fhr.jsonrpc4j.service.content.ContentServiceImpl;
import com.github.fhr.jsonrpc4j.service.user.User;
import com.github.fhr.jsonrpc4j.service.user.UserService;
import com.github.fhr.jsonrpc4j.service.user.UserServiceImpl;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.ProxyUtil;
import com.googlecode.jsonrpc4j.StreamServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public class StreamMultiServiceServer {
    public static void main(String[] args) throws IOException {
        UserService userService = new UserServiceImpl();
        ContentService contentService = new ContentServiceImpl();

        Object compositeService = ProxyUtil.createCompositeServiceProxy(
                StreamMultiServiceServer.class.getClassLoader(),
                new Object[]{contentService, userService},
                new Class<?>[]{ContentService.class,UserService.class},
                true);

        // create the jsonRpcServer
        JsonRpcServer jsonRpcServer = new JsonRpcServer(new ObjectMapper(), compositeService);
        // create the stream server
        int maxThreads = 50;
        int port = 52420;
        InetAddress bindAddress = InetAddress.getLoopbackAddress();
        ServerSocket serverSocket = new ServerSocket(port, 200, bindAddress);
        StreamServer streamServer = new StreamServer(jsonRpcServer, maxThreads, serverSocket);

        // start it, this method doesn't block
        streamServer.start();
    }
}
