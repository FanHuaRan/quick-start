package com.fhr.javaagent.simple.tran.agent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author Fan Huaran
 * created on 2018/12/12
 * @description
 */
public class Transformer implements ClassFileTransformer {
    public static final String classNumberReturns2 = "F:\\myspace\\quick-start\\java\\javaagent\\simple-transformer\\simple-tran-client\\src\\main\\resources\\TransClass.class.2";

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.equals("TransClass")) {
            return null;
        }

        return getBytesFromFile(classNumberReturns2);
    }

    public static byte[] getBytesFromFile(String fileName) {
        // precondition
        File file = new File(fileName);
        try(InputStream is = new FileInputStream(file)) {
            long length = file.length();
            byte[] bytes = new byte[(int) length];

            // Read in the bytes
            int offset = 0;
            int numRead;
            while (offset <bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }

            if (offset < bytes.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }
            return bytes;
        } catch (Exception e) {
            System.out.println("error occurs in _ClassTransformer!" + e.getClass().getName());
            return null;
        }
    }
}
