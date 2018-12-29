package com.fhr.netty.heartbeat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description
 */
public abstract class AbstractHeartbeatHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractHeartbeatHandler.class);

    private static final String PING_MSG = "ping";

    private static final String PONG_MSG = "pong";

    private static final AtomicLong HEAR_BEAT_COUNT = new AtomicLong(0L);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (PING_MSG.equals(msg)) {
            sendPongMsg(ctx);
        } else {
            handleData(ctx, msg);
        }
    }

    protected abstract void handleData(ChannelHandlerContext ctx, String msg);

    protected void sendPingMsg(ChannelHandlerContext context) {
        context.writeAndFlush(PING_MSG);
        logger.info(" sent ping msg to {} , count: {}", context.channel().remoteAddress(), HEAR_BEAT_COUNT.incrementAndGet());
    }

    protected void sendPongMsg(ChannelHandlerContext context) {
        context.writeAndFlush(PONG_MSG);
        HEAR_BEAT_COUNT.incrementAndGet();
        logger.info(" sent pong msg to {} , count: {}", context.channel().remoteAddress(), HEAR_BEAT_COUNT.incrementAndGet());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // IdleStateHandler 所产生的 IdleStateEvent 的处理逻辑.
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("---{} is active---", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("--- {} is inactive---", ctx.channel().remoteAddress());
    }

    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        logger.info("---READER_IDLE---");
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        logger.info("---WRITER_IDLE---");
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        logger.info("---ALL_IDLE---");
    }

}
