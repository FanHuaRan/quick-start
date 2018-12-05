package com.fhr.akka.minirpg;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

/**
 * @author Fan Huaran
 * created on 2018/11/28
 * @description 服务驱动类
 */
public class ServerApp {

    public static void main(String[] args) {
        // 创建actor系统
        ActorSystem mySystem = ActorSystem.create("rpgServer");
        // 创建游戏消息处理器并返回一个中间人
        ActorRef msgHandler = mySystem.actorOf(Props.create(MsgHandler.class));
        // 创建tcp服务组件并返回一个中间人
        ActorRef tcpServer = mySystem.actorOf(Props.create(TcpServer.class, msgHandler));
        // 通过tcp服务组件中间人向服务组件传递端口消息，以促使其打开端口接送服务
        tcpServer.tell(12345, ActorRef.noSender());
    }
}
