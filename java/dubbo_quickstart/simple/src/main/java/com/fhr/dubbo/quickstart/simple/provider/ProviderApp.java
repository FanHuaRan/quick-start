package com.fhr.dubbo.quickstart.simple.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Fan Huaran
 * created on 2018/12/3
 * @description
 */
public class ProviderApp {
    public static void main(String[] args) throws IOException {
        String configName = args.length > 0 ? args[0] : "provider.xml";
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[] {configName});
        context.start();
        // 按任意键退出
        System.in.read();
    }
}
