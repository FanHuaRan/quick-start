package com.github.fhr.jsonrpc4j.basic.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fhr.jsonrpc4j.service.user.UserService;
import com.github.fhr.jsonrpc4j.service.user.UserServiceImpl;
import com.googlecode.jsonrpc4j.JsonRpcServer;
import com.googlecode.jsonrpc4j.StreamServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public class StreamingSocketServer {
    public static void main(String[] args) throws IOException {
        // create the jsonRpcServer
        JsonRpcServer jsonRpcServer = new JsonRpcServer(new ObjectMapper(), new UserServiceImpl(), UserService.class);

        // create the stream server
        int maxThreads = 50;
        int port = 51420;
        InetAddress bindAddress = InetAddress.getLoopbackAddress();
        ServerSocket serverSocket = new ServerSocket(port, 200, bindAddress);
        StreamServer streamServer = new StreamServer(jsonRpcServer, maxThreads, serverSocket);

        // start it, this method doesn't block
        streamServer.start();
    }
}
