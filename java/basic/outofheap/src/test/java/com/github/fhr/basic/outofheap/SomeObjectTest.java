package com.github.fhr.basic.outofheap;

import org.junit.BeforeClass;
import org.junit.Test;

public class SomeObjectTest {

    private static final int COUNT = 100000;

    @BeforeClass
    public static void before() {
        doTestOutOfHeap();
        doTestHeap();
    }

    @Test
    public void testOutOfHeapPerformance() {
        doTestOutOfHeap();
    }

    @Test
    public void testHeapPerformance() {
        doTestHeap();
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