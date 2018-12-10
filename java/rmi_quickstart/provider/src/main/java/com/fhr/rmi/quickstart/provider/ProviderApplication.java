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
        // 实例化一个服务对象
        GreetingsService obj = new GreetingsServiceImpl(); // #1
        // 导出远程服务对象，在29190端口进行监听，这个stud是个存根，基于jdk动态代理实现
        GreetingsService stub = (GreetingsService) UnicastRemoteObject.exportObject(obj, 29190); // #2
        // 创建注册中心，监听1299端口，不过这个注册中心无法在多个应用间重用
        Registry registry = LocateRegistry.createRegistry(1299); //  默认注册端口1099
        // 将远程服务在注册中心上进行注册
        registry.rebind("Greeting", stub); // #4
    }
}
