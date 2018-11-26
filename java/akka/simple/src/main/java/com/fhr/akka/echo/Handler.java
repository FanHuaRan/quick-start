package com.fhr.akka.echo;

import akka.actor.UntypedActor;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.util.ByteString;

/**
 * @author Fan Huaran
 * created on 2018/11/26
 * @description
 */
public class Handler extends UntypedActor {

    public void onReceive(Object msg) throws Exception {
        System.out.println("Handler received:" + msg);
        if(msg instanceof Tcp.Received){
            final ByteString data = ((Tcp.Received)msg).data();
            getSender().tell(TcpMessage.write(data), getSelf());
        }else if(msg instanceof Tcp.ConnectionClosed){
            getContext().stop(getSelf());
        }
    }
}
