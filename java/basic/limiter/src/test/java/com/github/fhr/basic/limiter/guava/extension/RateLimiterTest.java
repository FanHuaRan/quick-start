package com.github.fhr.basic.limiter.guava.extension;

import org.junit.Test;

import static org.junit.Assert.*;

public class RateLimiterTest {

    @Test
    public void create() throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(3, 5);
        Thread.sleep(1000);
        for (int i = 0; i < 3; i++) {
            if (rateLimiter.tryAcquire(2)) {
                System.out.println("get the permit, index:" + i);
            } else {
                System.out.println("could not get the permit, index:" + i);
            }
        }

        Thread.sleep(5 * 1000);

        for (int i = 0; i < 16; i++) {
            if (rateLimiter.tryAcquire(1)) {
                System.out.println("get the permit, index:" + i);
            } else {
                System.out.println("could not get the permit, index:" + i);
            }
        }
    }
}