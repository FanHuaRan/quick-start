package com.fhr.dubbo.quickstart.simple.consumer;

import com.fhr.dubbo.quickstart.simple.IUserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Fan Huaran
 * created on 2018/12/3
 * @description
 */
public class ConsumerApp {
    public static void main(String[] args) throws IOException {
        String configName = args.length > 0 ? args[0] : "consumer.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {configName});
        context.start();
        IUserService userService = context.getBean(IUserService.class);
        System.out.println(userService.sayHello("java"));
        // 按任意键退出
        System.in.read();
    }
}
