package com.fhr.netty.heartbeat;

import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description
 */
public class DelimiterEncoder extends MessageToMessageEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
//        out.add(msg + "\r\n");
        if (msg.length() == 0) {
            return;
        }

        out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg + "\r\n"), StandardCharsets.UTF_8));
    }
}
