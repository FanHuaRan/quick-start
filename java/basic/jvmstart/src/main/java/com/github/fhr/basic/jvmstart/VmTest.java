package com.github.fhr.basic.jvmstart;

import sun.misc.VM;

/**
 * @author Fan Huaran
 * created on 2019/2/25
 * @description
 */
public class VmTest {

    public static void main(String[] args) {
        System.out.println(VM.isDirectMemoryPageAligned());
    }

}
