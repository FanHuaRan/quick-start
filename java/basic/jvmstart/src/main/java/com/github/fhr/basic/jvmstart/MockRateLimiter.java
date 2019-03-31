package com.github.fhr.basic.jvmstart;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Fan Huaran
 * created on 2019/3/1
 * @description
 */
public class MockRateLimiter {

    private final AtomicInteger counter = new AtomicInteger();

    private final int max_permit;

    public MockRateLimiter(int max_permit) {
        this.max_permit = max_permit;
    }

    public static MockRateLimiter create(int maxPermit) {
        return null;
    }

    public boolean accquire(int permit) {
        if(counter.get() < max_permit){
            return counter.addAndGet(permit) < max_permit;
        }

        return false;
    }

}
