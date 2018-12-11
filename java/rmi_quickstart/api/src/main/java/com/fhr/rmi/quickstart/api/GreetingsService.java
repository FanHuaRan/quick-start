package com.fhr.rmi.quickstart.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Fan Huaran
 * created on 2018/12/5
 * @description 定义一个服务契约，需要继承remote标记接口
 */
public interface GreetingsService extends Remote {
    /**
     * 想经过rmi远程调用的方法必须抛出RemoteException
     *
     * @param name
     * @return
     * @throws RemoteException
     */
    String sayHi(String name) throws RemoteException;

}
