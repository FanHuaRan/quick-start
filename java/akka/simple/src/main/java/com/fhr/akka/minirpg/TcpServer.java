package com.fhr.akka.minirpg;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;

import java.net.InetSocketAddress;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description tcp服务组件
 */
public class TcpServer extends UntypedActor {

    private final ActorRef msgHandler;

    public TcpServer(ActorRef msgHandler) {
        this.msgHandler = msgHandler;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Integer) {// 打开端口的消息
            final int port = (int) msg;
            startServer(port);// 启动服务器
        } else if (msg instanceof Tcp.Bound) {// 入站消息，进行读取
            getSender().tell(msg, getSelf());
        } else if (msg instanceof Tcp.CommandFailed) {// 消息错误
            getContext().stop(getSelf());
        } else if (msg instanceof Tcp.Connected) {// 连接消息，处理连接
            final Tcp.Connected connected = (Tcp.Connected) msg;
            getSender().tell(connected, getSelf());
            registerCodec(getSender());
        }
    }

    /**
     * 启动服务器
     *
     * @param port
     */
    private void startServer(int port) {
        final InetSocketAddress endpoint = new InetSocketAddress("localhost", port);
        final Object bindCmd = TcpMessage.bind(getSelf(), endpoint, 100);
        Tcp.get(getContext().system())
                .getManager()
                .tell(bindCmd, getSelf());
    }

    /**
     * 连接注册消息处理器
     *
     * @param connection
     */
    private void registerCodec(ActorRef connection) {
        final Props codecProps = Props.create(MsgCodec.class, connection, msgHandler);
        final ActorRef codec = getContext().actorOf(codecProps);
        connection.tell(TcpMessage.register(codec), getSelf());
    }
}
