package com.github.fhr.basic.outofheap;

/**
 * @author Fan Huaran
 * created on 2019/2/20
 * @description
 */
public class Main {
    private static final int COUNT = 100000;

    public static void main(String[] args) {
        /// just for jit
        doTestOutOfHeap();
        doTestHeap();

        /// test
        long startTime = System.nanoTime();
        doTestOutOfHeap();
        System.out.printf("out of heap cost nanos:%d\n", System.nanoTime() - startTime);

        startTime = System.nanoTime();
        doTestHeap();
        System.out.printf("heap cost nanos:%d\n", System.nanoTime() - startTime);
    }

    private static void doTestHeap() {
        for (int i = 0; i < COUNT; i++) {
            SomeObject memoryObject = new SomeObject();
            memoryObject.setSomeInt(1);
            memoryObject.setSomeLong(1L);
            memoryObject.getSomeInt();
            memoryObject.getSomeLong();
        }
    }

    private static void doTestOutOfHeap() {
        Direct direct = Direct.getInstance();

        for (int i = 0; i < COUNT; i++) {
            SomeMemoryObject memoryObject = new SomeMemoryObject(direct);
            memoryObject.setSomeInt(1);
            memoryObject.setSomeLong(1L);
            memoryObject.getSomeInt();
            memoryObject.getSomeLong();
        }
    }
}
