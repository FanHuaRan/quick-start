package com.github.fhr.basic.limiter.guava;

import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;

/**
 * Created by Huaran Fan on 2019/3/2
 *
 * @description
 */
public class SmoothBurstyTest {

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = RateLimiter.create(3);
        Thread.sleep(1000);
        for (int i = 0; i < 3; i++) {
            if (rateLimiter.tryAcquire(2)) {
                System.out.println("get the permit, index:" + i);
            } else {
                System.out.println("could not get the permit, index:" + i);
            }
        }

        Thread.sleep(5 * 1000);

        for (int i = 0; i < 12; i++) {
            if (rateLimiter.tryAcquire(1)) {
                System.out.println("get the permit, index:" + i);
            } else {
                System.out.println("could not get the permit, index:" + i);
            }
        }
    }

}
