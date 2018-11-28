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
 * @description
 */
public class MsgCodec extends UntypedActor {

    private static final Gson GSON = new Gson();

    private final ActorRef connection;
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
            decodeMsg();
        } else if (msg instanceof Tcp.ConnectionClosed) {
            getContext().stop(getSelf());
        } else if (msg instanceof GameMessage) {
            final ByteString data = encodeMsg(msg);
            connection.tell(TcpMessage.write(data), getSelf());
        }
    }

    private void decodeMsg() {
        while (buf.length() > 8) {
            final ByteIterator it = buf.iterator();
            final int msgId = it.getInt(ByteOrder.BIG_ENDIAN);
            final int jsonLength = it.getInt(ByteOrder.BIG_ENDIAN);

            if (buf.length() >= 8 + jsonLength) {
                final Object msg = decodeMsg(msgId, buf.slice(8, 8 + jsonLength));
                buf = buf.drop(8 + jsonLength);
                msgHandler.tell(msg, getSelf());
            }
        }
    }

    private Object decodeMsg(int msgId, ByteString jsonData) {
        final Class<?> msgClass = MsgRegistry.getMsgClass(msgId);
        final Reader reader = new InputStreamReader(jsonData.iterator().asInputStream(), StandardCharsets.UTF_8);
        return GSON.fromJson(reader, msgClass);
    }

    private ByteString encodeMsg(Object msg) {
        final int msgId = MsgRegistry.getMsgId(msg);
        final byte[] jsonBytes = GSON.toJson(msg)
                .getBytes(StandardCharsets.UTF_8);

        final ByteStringBuilder bsb = new ByteStringBuilder();
        bsb.putInt(msgId, ByteOrder.BIG_ENDIAN);
        bsb.putInt(jsonBytes.length, ByteOrder.BIG_ENDIAN);
        bsb.putBytes(jsonBytes);

        return bsb.result();
    }



}

