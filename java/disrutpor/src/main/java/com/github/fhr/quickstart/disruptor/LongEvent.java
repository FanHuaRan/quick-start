package com.github.fhr.quickstart.disruptor;

/**
 * @author Fan Huaran
 * created on 2019/8/1
 * @description
 */
public class LongEvent {
    private long value;

    public void set(long value)
    {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
