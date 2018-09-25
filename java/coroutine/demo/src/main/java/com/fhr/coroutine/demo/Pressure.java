package com.fhr.coroutine.demo;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.strands.Strand;

import javax.swing.plaf.TableHeaderUI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Fan Huaran
 * created on 2018/9/25
 * @description
 */
public class Pressure {

    public static void main(String[] args) throws InterruptedException {
        int FiberNumber = 1_000_000;
        CountDownLatch latch = new CountDownLatch(1);
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < FiberNumber; i++) {
            new Fiber<>(() -> {
                counter.incrementAndGet();
                if (counter.get() == FiberNumber) {
                    System.out.println("done");
                }
                Strand.sleep(1000000);
            }).start();
        }
        latch.await();
    }


}
