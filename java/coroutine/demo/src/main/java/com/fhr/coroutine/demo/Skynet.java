package com.fhr.coroutine.demo;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;

/**
 * @author Fan Huaran
 * created on 2018/9/25
 * @description 大致的逻辑是先生成10个Fiber，每个Fiber再生成10个Fiber，直到生成1百万个Fiber，然后每个Fiber做加法累积计算，并把结果发到channel里，这样一直递归到根Fiber。后将最终结果发到channel。
 */
public class Skynet {

    private static final int RUNS = 4;

    static void skynet(Channel<Long> c, long num, int size, int div) throws SuspendExecution, InterruptedException {
        if (size == 1) {
            c.send(num);
            return;
        }

        Channel<Long> rc = Channels.newChannel(div);
        long sum = 0L;
        for (int i = 0; i < div; i++) {
            long subNum = num + i * (size / div);
            new Fiber(() -> skynet(rc, subNum, size / div, div)).start();
        }

        Long v = null;
        while ((v = rc.receive()) != null) {
            sum += v;
        }
        c.send(sum);
    }

    public static void main(String[] args) throws Exception {
        //这里跑4次，是为了让JVM预热好做优化，所以我们以最后一个结果为准。
        for (int i = 1; i <= RUNS; i++) {
            long start = System.nanoTime();

            Channel<Long> c = Channels.newChannel(-1);
            new Fiber(() -> skynet(c, 0, 1_000_000, 10)).start();
            long result = c.receive();

            long elapsed = (System.nanoTime() - start) / 1_000_000;
            System.out.println(i + ": " + result + " (" + elapsed + " ms)");
        }
    }

}
