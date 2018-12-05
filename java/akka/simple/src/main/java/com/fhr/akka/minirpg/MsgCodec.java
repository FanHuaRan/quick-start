package com.fhr.akka.minirpg;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteIterator;
import akka.util.ByteString;
import akka.util.ByteStringBuilder;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description 消息编码和解码
 */
public class MsgCodec extends UntypedActor {

    private static final Gson GSON = new Gson();

    private final ActorRef connection;// MsgCodec与连接 是1:1关系，因为单个tcp连接这儿的处理是封闭串行的所以不存在线程问题
    private final ActorRef msgHandler;

    private ByteString buf = ByteString.empty();

    public MsgCodec(ActorRef connection, ActorRef msgHandler) {
        this.connection = connection;
        this.msgHandler = msgHandler;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Tcp.Received) {
            final ByteString data = ((Tcp.Received) msg).data();
            buf = buf.concat(data);
            doDecodeMsg();
        } else if (msg instanceof Tcp.ConnectionClosed) {
            getContext().stop(getSelf());
        } else if (msg instanceof GameMessage) {
            final ByteString data = encodeMsg(msg);
            connection.tell(TcpMessage.write(data), getSelf());
        }
    }

    /**
     * 解码消息，考虑到了半包和粘包问题
     */
    private void doDecodeMsg() {
        while (buf.length() > 8) {// 循环处理，解决粘包问题
            final ByteIterator it = buf.iterator();
            final int msgId = it.getInt(ByteOrder.BIG_ENDIAN);//消息ID
            final int jsonLength = it.getInt(ByteOrder.BIG_ENDIAN);//json长度

            if (buf.length() >= 8 + jsonLength) {// 数据已经凑齐
                final Object msg = doDecodeMsg(msgId, buf.slice(8, 8 + jsonLength));
                buf = buf.drop(8 + jsonLength);
                msgHandler.tell(msg, getSelf());
            }else{// 数据未凑齐,return，解析半包问题
                return;
            }
        }
    }

    /**
     * 解码消息
     *
     * @param msgId
     * @param jsonData
     * @return
     */
    private Object doDecodeMsg(int msgId, ByteString jsonData) {
        final Class<?> msgClass = MsgRegistry.getMsgClass(msgId);
        final Reader reader = new InputStreamReader(jsonData.iterator().asInputStream(), StandardCharsets.UTF_8);
        return GSON.fromJson(reader, msgClass);
    }

    /**
     * 编码消息
     *
     * @param msg
     * @return
     */
    private ByteString encodeMsg(Object msg) {
        final int msgId = MsgRegistry.getMsgId(msg);
        final byte[] jsonBytes = GSON.toJson(msg).getBytes(StandardCharsets.UTF_8);

        final ByteStringBuilder bsb = new ByteStringBuilder();
        bsb.putInt(msgId, ByteOrder.BIG_ENDIAN);
        bsb.putInt(jsonBytes.length, ByteOrder.BIG_ENDIAN);
        bsb.putBytes(jsonBytes);

        return bsb.result();
    }
}

