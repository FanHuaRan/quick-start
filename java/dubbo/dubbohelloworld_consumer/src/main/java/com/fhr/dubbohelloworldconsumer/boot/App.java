package com.fhr.dubbohelloworldconsumer.boot;

import com.fhr.dubbohelloworldconsumer.service.IUserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description:
 * @author:
 * @create: 2018-03-25 00:44
 **/
public class App {

    public static void main(String[] args) {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[]{"consumer.xml"});
        context.start();
        final IUserService userService = context.getBean("userService", IUserService.class); // 获取远程服务代理
        String hello = userService.sayHello("world"); // 执行远程方法
        System.out.println("===================================");
        System.out.println(hello); // 显示调用结果
        System.out.println("===================================");
    }
}
