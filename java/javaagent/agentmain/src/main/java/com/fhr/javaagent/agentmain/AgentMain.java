package com.fhr.javaagent.agentmain;

import javax.tools.JavaCompiler;
import java.lang.instrument.ClassDefinition;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author Fan Huaran
 * created on 2019/10/11
 * @description
 */
public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst)
            throws ClassNotFoundException, UnmodifiableClassException,
            InterruptedException {
        System.out.println("Agent Main Start");
        System.out.println("agent args:" + agentArgs);
        System.out.println("isNativeMethodPrefixSupported:" + inst.isNativeMethodPrefixSupported());
        System.out.println("isRetransformClassesSupported:" + inst.isRetransformClassesSupported());
        System.out.println("isRedefineClassesSupported:" + inst.isRedefineClassesSupported());
//        inst.addTransformer(new Transformer (), true);
//        inst.retransformClasses(TransClass.class);
        // or
        ClassDefinition def = new ClassDefinition(TransClass.class,Transformer.getClassBytes());
        inst.redefineClasses(def);
        System.out.println("Agent Main Done");
    }

}
