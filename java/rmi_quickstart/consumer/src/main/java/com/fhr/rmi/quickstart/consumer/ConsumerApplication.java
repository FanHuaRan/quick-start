package com.fhr.rmi.quickstart.consumer;

import com.fhr.rmi.quickstart.api.GreetingsService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Fan Huaran
 * created on 2018/12/5
 * @description
 */
public class ConsumerApplication {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(1299); //  默认注册端口1099
        GreetingsService stub = (GreetingsService) registry.lookup("Greeting"); // #2
        System.out.println(stub.sayHi("rmi"));
    }

}
