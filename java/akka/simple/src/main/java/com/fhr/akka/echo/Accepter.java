package com.fhr.akka.echo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;

import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * @author Fan Huaran
 * created on 2018/11/26
 * @description
 */
public class Accepter extends UntypedActor {
    private final ActorRef tcpManager;

    public Accepter(ActorRef tcpManager) {
        this.tcpManager = tcpManager;
    }

    public void onReceive(Object msg) throws Exception {
        System.out.println("Accepter received:" + msg);
        if (msg instanceof Integer) {
            final int port = (Integer) msg;
            final InetSocketAddress endPoint = new InetSocketAddress("localhost", port);
            final Object cmd = TcpMessage.bind(getSelf(), endPoint, 100);
            tcpManager.tell(cmd, getSelf());
        } else if (msg instanceof Tcp.Bound) {
            tcpManager.tell(msg, getSelf());
        } else if (msg instanceof Tcp.CommandFailed) {
            getContext().stop(getSelf());
        } else if (msg instanceof Tcp.Connected) {
            final Tcp.Connected connected = (Tcp.Connected) msg;
            final ActorRef handler = getContext().actorOf(Props.create(Handler.class));
            getSender().tell(TcpMessage.register(handler), getSelf());
        }
    }
}
