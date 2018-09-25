package com.fhr.coroutine.demo;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;

/**
 * @author Fan Huaran
 * created on 2018/9/25
 * @description 大致的逻辑是先生成10个Fiber，每个Fiber再生成10个Fiber，直到生成1百万个Fiber，然后每个Fiber做加法累积计算，并把结果发到channel里，这样一直递归到根Fiber。后将最终结果发到channel。
 */
public class Skynet2 {
    static final int RUNS = 4;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < RUNS; i++) {
            Instant start = Instant.now();

            long result = new Fiber<>(() -> skynet(0, 1_000_000, 10)).start().get();

            Duration elapsed = Duration.between(start, Instant.now());
            System.out.println((i + 1) + ": " + result + " (" + elapsed.toMillis() + " ms)");
        }
    }

    static long skynet(long num, int size, int div) throws SuspendExecution, InterruptedException {
        try {
            if (size == 1)
                return num;

            Fiber<Long>[] children = new Fiber[div];
            long sum = 0L;
            for (int i = 0; i < div; i++) {
                long subNum = num + i * (size / div);
                children[i] = new Fiber<>(() -> skynet(subNum, size / div, div)).start();
            }
            for (Fiber<Long> c : children)
                sum += c.get();
            return sum;
        } catch (ExecutionException e) {
            throw (RuntimeException) e.getCause();
        }
    }

}

