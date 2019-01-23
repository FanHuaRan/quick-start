package com.fhr.netty.heartbeat.server;

import com.fhr.netty.heartbeat.DelimiterEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.StandardCharsets;

/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description
 */
public class ServerBoot {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(4);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            // 添加空闲状态处理器
                            p.addLast(new IdleStateHandler(0, 0, 10));
                            // 添加行分隔符解码器
                            p.addLast(new LineBasedFrameDecoder(1024));
                            // 添加字符串解码器
                            p.addLast(new StringDecoder(StandardCharsets.UTF_8));
                            // 添加请求处理器（内含心跳逻辑）
                            p.addLast(new ServerHandler());
                            // 添加分隔符编码器
                            p.addLast(new DelimiterEncoder());
                        }
                    });

            Channel ch = bootstrap.bind(12345).sync().channel();
//            new  Thread(()->{
//                bossGroup.shutdownGracefully();
//                workGroup.shutdownGracefully();
//            }).start();
            ch.closeFuture().sync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
