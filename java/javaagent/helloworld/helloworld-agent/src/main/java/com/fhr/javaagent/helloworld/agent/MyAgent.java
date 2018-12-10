package com.fhr.javaagent.helloworld.agent;

import java.lang.instrument.Instrumentation;

/**
 * Created by Huaran Fan on 2018/12/10
 *
 * @description
 */
public class MyAgent {

    public static void premains(String agentOps, Instrumentation instrumentation){
        System.out.println("=========premain参数个数为2方法执行========，My agentOps = [" + agentOps + "].\");");
        System.out.println(agentOps);
    }

    public static void premain(String agentOps){
        System.out.println("=========premain参数个数为1方法执行========，My agentOps = [" + agentOps + "].");
        System.out.println(agentOps);
    }

}
