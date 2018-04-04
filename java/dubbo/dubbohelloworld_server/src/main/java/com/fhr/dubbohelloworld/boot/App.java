package com.fhr.dubbohelloworld.boot;

import com.fhr.dubbohelloworld.service.IUserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @description:
 * @author:
 * @create: 2018-03-25 00:28
 **/
public class App {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"ApplicationContext.xml"});
        context.start();
        // 按任意键退出
        System.in.read();
    }
}
