package com.fhr.akka.helloworld;

import akka.actor.UntypedActor;

/**
 * @author Fan Huaran
 * created on 2018/11/26
 * @description
 */
public class MyActor extends UntypedActor {
    public void onReceive(Object message) throws Exception {
        System.out.println(message);
    }
}
