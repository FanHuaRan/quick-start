package com.fhr.akka.helloworld;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Fan Huaran
 * created on 2018/11/26
 * @description
 */
public class HelloWorldMain {

    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("mySystem");
        ActorRef myActor = actorSystem.actorOf(Props.create(MyActor.class), "myActor");
        myActor.tell("Hello, World!", ActorRef.noSender());
        actorSystem.shutdown();
    }

}
