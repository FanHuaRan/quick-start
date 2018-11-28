package com.fhr.akka.minirpg;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description
 */
public class ServerApp {

    public static void main(String[] args) {
        ActorSystem mySystem = ActorSystem.create("rpgServer");
        ActorRef msgHandler = mySystem.actorOf(Props.create(MsgHandler.class));
        ActorRef tcpServer = mySystem.actorOf(Props.create(TcpServer.class, msgHandler));
        tcpServer.tell(12345, ActorRef.noSender());
    }

}
