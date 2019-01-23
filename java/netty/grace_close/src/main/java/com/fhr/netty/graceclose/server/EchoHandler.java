package com.fhr.netty.graceclose.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Fan Huaran
 * created on 2019/1/23
 * @description 业务组件，echo业务
 */
public class EchoHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(EchoHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("receive {} msg: {}", ctx.channel().remoteAddress(), msg);
        ctx.writeAndFlush(msg);
        // 以下代码可以让服务端主动关闭客户端
        ctx.channel().close().sync();
    }
}
