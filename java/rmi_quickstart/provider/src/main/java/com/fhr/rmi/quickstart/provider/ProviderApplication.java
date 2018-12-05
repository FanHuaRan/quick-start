package com.fhr.rmi.quickstart.provider;

import com.fhr.rmi.quickstart.api.GreetingsService;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Fan Huaran
 * created on 2018/12/5
 * @description
 */
public class ProviderApplication {

    public static void main(String[] args) throws RemoteException {
        GreetingsService obj = new GreetingsServiceImpl(); // #1
        GreetingsService stub = (GreetingsService) UnicastRemoteObject.exportObject(obj, 29190); // #2

        Registry registry = LocateRegistry.createRegistry(1299); //  默认注册端口1099

        registry.rebind("Greeting", stub); // #4
    }

}
