package com.fhr.javaagent.premain;

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
        if (!className.equals("com/fhr/javaagent/premain/TransClass")) {
            return null;
        }
        try {
            return IOUtils.toByteArray(this.getClass().getClassLoader().getResource("TransClass").openStream());
        } catch (Exception er) {
            er.printStackTrace();
            return null;
        }
    }

}
