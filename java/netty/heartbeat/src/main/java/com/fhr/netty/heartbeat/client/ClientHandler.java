package com.fhr.netty.heartbeat.client;

import com.fhr.netty.heartbeat.AbstractHeartbeatHandler;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description
 */
public class ClientHandler extends AbstractHeartbeatHandler {
    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    protected void handleData(ChannelHandlerContext ctx, String msg) {
        logger.info("receive server msg : " + msg);
    }

    @Override
    protected void handleAllIdle(ChannelHandlerContext ctx) {
        super.handleAllIdle(ctx);
        sendPingMsg(ctx);
    }
}
