package com.fhr.javaagent.premain;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author Fan Huaran
 * created on 2019/10/11
 * @description
 */
public class Premain {
    public static void premain(String agentArgs, Instrumentation inst)
            throws ClassNotFoundException,UnmodifiableClassException {
        System.out.println("premain start");
        System.out.println("agent args:" + agentArgs);
        System.out.println("isNativeMethodPrefixSupported:" + inst.isNativeMethodPrefixSupported());
        System.out.println("isRetransformClassesSu pported:" + inst.isRetransformClassesSupported());
        System.out.println("isRedefineClassesSupported:" + inst.isRedefineClassesSupported());
//        inst.addTransformer(new Transformer (), true);
        inst.addTransformer(new Transformer());
        inst.retransformClasses();
        inst.getAllLoadedClasses();
        System.out.println("String is can modify?" + inst.isModifiableClass(String.class));
        System.out.println("premain done");
    }
}
