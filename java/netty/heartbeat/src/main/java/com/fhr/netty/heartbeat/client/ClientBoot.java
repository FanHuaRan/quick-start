package com.fhr.netty.heartbeat.client;

import com.fhr.netty.heartbeat.DelimiterEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description
 */
public class ClientBoot {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);

        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            // 添加空闲状态处理器
                            p.addLast(new IdleStateHandler(0, 0, 10));
                            // 添加行分隔符解码器
//                        p.addLast(new DelimiterBasedFrameDecoder(1024,true,Unpooled.copiedBuffer("&&".getBytes(StandardCharsets.UTF_8))));
                            p.addLast(new LineBasedFrameDecoder(1024));
                            // 添加字符串解码器
                            p.addLast(new StringDecoder(StandardCharsets.UTF_8));
//                        p.addLast(new StringEncoder(StandardCharsets.UTF_8));
                            // 添加请求处理器
                            p.addLast(new ClientHandler());
                            // 添加行分隔符编码器
                            p.addLast(new DelimiterEncoder());
                        }
                    });

            ChannelFuture future = bootstrap.connect("127.0.0.1", 12345).sync();
            Channel channel = future.channel();

            // 模拟随机发送
            Random random = new Random();
            for (int i = 0; i < 1000; i++) {
                int randomValue = random.nextInt(100);
                Thread.sleep(randomValue * 1000);
                channel.writeAndFlush("TEST_" + randomValue);
            }

            // 关闭channel
            channel.close().sync();

        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
