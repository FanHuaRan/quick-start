package com.fhr.javaagent.agentmain;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author Fan Huaran
 * created on 2019/10/11
 * @description
 */
public class Transformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals("com/fhr/javaagent/agentmain/TransClass")) {
            return null;
        }
        return getClassBytes();
    }

    public static byte[] getClassBytes(){
        try {
            return IOUtils.toByteArray(Transformer.class.getClassLoader().getResource("TransClass").openStream());
        } catch (Exception er) {
            er.printStackTrace();
            return null;
        }
    }

}
