package com.fhr.netty.graceclose.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description
 */
public class ClientBoot {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            // 添加字符串编码器
                            p.addLast(new StringEncoder(StandardCharsets.UTF_8));
                        }
                    });

            ChannelFuture future = bootstrap.connect("127.0.0.1", 12345).sync();
            Channel channel = future.channel();
            channel.writeAndFlush("TEST\r\n");

            // 5s 后关闭
            Thread.sleep(5 * 1000);

            // 关闭channel
            channel.close().sync();
        } finally {
            // 释放线程池资源
            workerGroup.shutdownGracefully();
        }
    }
}
