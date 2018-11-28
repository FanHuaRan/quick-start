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
 * @description
 */
public class TcpServer extends UntypedActor {

    private final ActorRef msgHandler;

    public TcpServer(ActorRef msgHandler) {
        this.msgHandler = msgHandler;
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg instanceof Integer) {
            final int port = (int) msg;
            startServer(port);
        } else if (msg instanceof Tcp.Bound) {
            getSender().tell(msg, getSelf());
        } else if (msg instanceof Tcp.CommandFailed) {
            getContext().stop(getSelf());
        } else if (msg instanceof Tcp.Connected) {
            final Tcp.Connected connected = (Tcp.Connected) msg;
            getSender().tell(connected, getSelf());
            registerCodec(getSender());
        }
    }

    private void startServer(int port) {
        final InetSocketAddress endpoint = new InetSocketAddress("localhost", port);
        final Object bindCmd = TcpMessage.bind(getSelf(), endpoint, 100);
        Tcp.get(getContext().system()).getManager().tell(bindCmd, getSelf());
    }

    private void registerCodec(ActorRef connection) {
        final Props codecProps = Props.create(MsgCodec.class, connection, msgHandler);
        final ActorRef codec = getContext().actorOf(codecProps);
        connection.tell(TcpMessage.register(codec), getSelf());
    }
}
