package com.fhr.netty.graceclose;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Fan Huaran
 * created on 2018/12/29
 * @description 分隔符编码器，在每个消息后面加上crlf
 */
public class DelimiterEncoder extends MessageToMessageEncoder<String> {

    @Override
    protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
        if (msg.length() == 0) {
            return;
        }
        // 也可以直接输出ByteBuf，这样就不用再挂载一个StringEncoder
        //  out.add(ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(msg + "\r\n"), StandardCharsets.UTF_8));
        out.add(msg + "\r\n");
    }
}
