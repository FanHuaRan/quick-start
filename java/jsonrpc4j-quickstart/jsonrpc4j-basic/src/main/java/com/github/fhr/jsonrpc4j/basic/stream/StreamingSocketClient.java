package com.github.fhr.jsonrpc4j.basic.stream;

import com.googlecode.jsonrpc4j.JsonRpcClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author Fan Huaran
 * created on 2019/11/1
 * @description
 */
public class StreamingSocketClient {

    public static void main(String[] args) throws IOException {
        JsonRpcClient rpcClient = new JsonRpcClient();
        Socket socket = new Socket();
        SocketAddress serverAddress = new InetSocketAddress(InetAddress.getLoopbackAddress(), 51420);
        socket.connect(serverAddress);

        rpcClient.invoke("createUser", new Object[]{"bob", "the builder"}, socket.getOutputStream());
    }
}
