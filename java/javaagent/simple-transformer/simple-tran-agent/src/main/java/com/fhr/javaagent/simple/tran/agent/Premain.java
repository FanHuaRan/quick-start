package com.fhr.javaagent.simple.tran.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * @author Fan Huaran
 * created on 2018/12/12
 * @description
 */
public class Premain {

    public static void premain(String agentArgs,Instrumentation inst)
            throws ClassNotFoundException,UnmodifiableClassException {
        inst.addTransformer(new Transformer());
    }

}
