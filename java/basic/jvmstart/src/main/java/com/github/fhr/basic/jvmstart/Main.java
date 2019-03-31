package com.github.fhr.basic.jvmstart;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author Fan Huaran
 * created on 2019/2/22
 * @description
 */
public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        class Simple extends RecursiveTask {

            private final int left;

            private final int right;

            public Simple(int left, int right) {
                this.left = left;
                this.right = right;
            }

            @Override
            protected Object compute() {
                if (right == left) {
                    return right;
                }
                if (right - left == 1) {
                    return right + left;
                }

                int offset = (right - left) >> 1;
                Simple leftTask = new Simple(left, left + offset - 1);
                Simple rightTask = new Simple(left + offset, right);
                leftTask.fork();
                rightTask.fork();
                return (int) leftTask.join() + (int) rightTask.join();
            }
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 1);
        System.out.println(forkJoinPool.submit(new Simple(0, 20)).get());
    }
}
