package com.fhr.netty.graceclose.server;

import com.fhr.netty.graceclose.DelimiterEncoder;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description
 */
public class ServerBoot {
    private static final Logger logger = LoggerFactory.getLogger(ServerBoot.class);

    private static NioEventLoopGroup bossGroup;
    private static NioEventLoopGroup workGroup;

    public static void main(String[] args) {
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() << 1);

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline p = socketChannel.pipeline();
                            /// 入站处理器相关，入站处理器会按照add的先后顺序正向执行
                            // 添加行分割符解码器
                            p.addLast(new LineBasedFrameDecoder(1024));
                            // 添加字符串解码器
                            p.addLast(new StringDecoder(StandardCharsets.UTF_8));
                            // 添加业务处理器
                            p.addLast(new EchoHandler());

                            /// 出站处理器相关，出站处理器会按照add的先后顺序逆向执行
                            // 添加字符串编码器
                            p.addLast(new StringEncoder(StandardCharsets.UTF_8));
                            // 添加行分隔符编码器
                            p.addLast(new DelimiterEncoder());
                        }
                    });
            addHook();
            Channel ch = bootstrap.bind(12345).sync().channel();
            ch.closeFuture().sync();
            logger.info("server socket close success.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            // 关闭操作是幂等的
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private static void addHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            logger.info("start execute hook ...");
            ServerBoot.close();
        }));
    }

    public static void close() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully(2, 15, TimeUnit.SECONDS);
        }
        if (workGroup != null) {
            workGroup.shutdownGracefully(2, 15, TimeUnit.SECONDS);
        }
    }
}
