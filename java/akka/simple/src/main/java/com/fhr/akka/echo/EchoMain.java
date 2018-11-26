package com.fhr.akka.echo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.io.Tcp;

/**
 * @author Fan Huaran
 * created on 2018/11/26
 * @description
 */
public class EchoMain {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("mySystem");
        ActorRef tcpManager = Tcp.get(actorSystem).getManager();
        Props acceptorProps = Props.create(Accepter.class, tcpManager);
        ActorRef accepter = actorSystem.actorOf(acceptorProps,"accepter");
        accepter.tell(12345,ActorRef.noSender());
    }
}
