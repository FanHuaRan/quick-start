package com.github.fhr.basic.outofheap;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author Fan Huaran
 * created on 2019/2/20
 * @description
 */
public class Direct {
    private static Unsafe unsafe;
    private static boolean AVAILABLE = false;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            AVAILABLE = true;
        } catch (Exception e) {
            // NOOP: throw exception later when allocating memory
        }
    }

    public static boolean isAvailable() {
        return AVAILABLE;
    }

    private static Direct INSTANCE = null;

    public static Direct getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Direct();
        }
        return INSTANCE;
    }

    private Direct() {

    }

    public long alloc(long size) {
        if (!AVAILABLE) {
            throw new IllegalStateException("sun.misc.Unsafe is not accessible!");
        }
        return unsafe.allocateMemory(size);
    }

    public void free(long address) {
        unsafe.freeMemory(address);
    }

    public final long getLong(long address) {
        return unsafe.getLong(address);
    }

    public final void putLong(long address, long value) {
        unsafe.putLong(address, value);
    }

    public final int getInt(long address) {
        return unsafe.getInt(address);
    }

    public final void putInt(long address, int value) {
        unsafe.putInt(address, value);
    }
}
