package com.fhr.netty.hearbeatv2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description ping-pong型心跳入站处理器
 */
public class HeartbeatHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(HeartbeatHandler.class);

    private static final String PING_MSG = "ping";

    private static final String PONG_MSG = "pong";

    private final AtomicLong HEAR_BEAT_COUNT = new AtomicLong(0L);

    private final boolean handleReaderIdle;

    private final boolean handleWriterIdle;

    private final boolean handleAllIdle;

    public HeartbeatHandler(boolean handleReaderIdle, boolean handleWriterIdle, boolean handleAllIdle) {
        this(true, handleReaderIdle, handleWriterIdle, handleAllIdle);
    }

    public HeartbeatHandler(boolean autoRelease, boolean handleReaderIdle, boolean handleWriterIdle, boolean handleAllIdle) {
        super(autoRelease);
        this.handleReaderIdle = handleReaderIdle;
        this.handleWriterIdle = handleWriterIdle;
        this.handleAllIdle = handleAllIdle;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (PING_MSG.equals(msg)) {
            sendPongMsg(ctx);
            return;
        }

        ctx.fireChannelRead(msg);
    }

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
                    if (handleReaderIdle) {
                        handleReaderIdle(ctx);
                    }
                    break;
                case WRITER_IDLE:
                    if (handleWriterIdle) {
                        handleWriterIdle(ctx);
                    }
                    break;
                case ALL_IDLE:
                    if (handleAllIdle) {
                        handleAllIdle(ctx);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.info("---{} is active---", ctx.channel().remoteAddress());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        logger.info("--- {} is inactive---", ctx.channel().remoteAddress());
        // TODO 检测到连接关闭，这儿可以根据业务情况进行处理:比如客户端进行断线重连
    }

    private void handleReaderIdle(ChannelHandlerContext ctx) {
        logger.info("--- {} is READER_IDLE---", ctx.channel().remoteAddress());
        // 服务端可以不发送心跳，直接关闭连接，这个看业务
        sendPingMsg(ctx);
    }

    private void handleWriterIdle(ChannelHandlerContext ctx) {
        logger.info("--- {} is ---WRITER_IDLE---", ctx.channel().remoteAddress());
        sendPingMsg(ctx);
    }

    private void handleAllIdle(ChannelHandlerContext ctx) {
        logger.info("--- {} is---ALL_IDLE---", ctx.channel().remoteAddress());
        sendPingMsg(ctx);
    }
}
