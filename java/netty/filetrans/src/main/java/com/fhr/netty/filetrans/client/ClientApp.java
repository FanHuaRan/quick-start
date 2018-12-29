package com.fhr.netty.filetrans.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Fan Huaran
 * created on 2018/12/20
 * @description
 */
public class ClientApp {
    public static void main(String[] args) throws InterruptedException, IOException {
        Socket socket = new Socket("127.0.0.1",8888);
        OutputStream outputStream = socket.getOutputStream();
        String req = "request";
        outputStream.write(req.getBytes());
        InputStream inputStream = socket.getInputStream();
        byte[] buf = new byte[1024 * 8];
        int read = -1;
        int totalRead = 0;
        while ((read = inputStream.read(buf)) != -1){
            totalRead += read;
            System.out.println(totalRead);
        }
    }
}
