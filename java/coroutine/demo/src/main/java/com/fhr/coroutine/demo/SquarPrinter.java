package com.fhr.coroutine.demo;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.channels.Channel;
import co.paralleluniverse.strands.channels.Channels;

/**
 * @author Fan Huaran
 * created on 2018/9/25
 * @description
 */
public class SquarPrinter {

    public static void main(String[] args) throws SuspendExecution, InterruptedException {
        Channel<Integer> naturals = Channels.newChannel(-1);
        Channel<Integer> squares = Channels.newChannel(-1);

        new Fiber<>(() -> counter(naturals)).start();

        new Fiber<>(() -> squarer(naturals, squares)).start();

        printer(squares);
    }

    private static void printer(Channel<Integer> out) throws InterruptedException, SuspendExecution {
        Integer v;
        while ((v = out.receive()) != null) {
            System.out.println(v);
        }
    }

    private static void counter(Channel<Integer> in) throws InterruptedException, SuspendExecution {
        for (int i = 1; i <= 10; i++) {
            in.send(i);
        }
    }

    private static void squarer(Channel<Integer> in, Channel<Integer> out) throws InterruptedException, SuspendExecution {
        Integer v;
        while ((v = in.receive()) != null) {
            out.send(v * v);
        }
    }

}
