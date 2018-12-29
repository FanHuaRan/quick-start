package com.fhr.netty.filetrans.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author Fan Huaran
 * created on 2018/12/20
 * @description
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf)msg;

        System.out.println(byteBuf.toString());
        File sendFile = new File("E:\\softpack\\flink-1.6.2-bin-scala_2.11.tgz");
        long fileSize = sendFile.length();
        ctx.writeAndFlush(fileSize);

        FileInputStream fileInputStream = new FileInputStream(sendFile);
        byte[] bufRead = new byte[8192 * 2];
        int readLen = -1;
        int totalSendLen = 0;
        while ((readLen = fileInputStream.read(bufRead)) != -1){
            totalSendLen += readLen;
            byteBuf.clear();
            byteBuf.writeBytes(bufRead, 0, readLen);
            ctx.writeAndFlush(Unpooled.copiedBuffer(byteBuf));
        }

        //ctx.write(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);

        System.out.println(totalSendLen);
        ctx.channel().closeFuture().sync();
    }

//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.write(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
//        super.channelReadComplete(ctx);
//        System.out.println("complete");
//        ctx.close();
//    }
}
