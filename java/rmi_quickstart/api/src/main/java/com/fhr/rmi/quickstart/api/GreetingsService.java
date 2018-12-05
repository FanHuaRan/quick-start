package com.fhr.rmi.quickstart.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Fan Huaran
 * created on 2018/12/5
 * @description
 */
public interface GreetingsService extends Remote {

    String sayHi(String name) throws RemoteException;

}
