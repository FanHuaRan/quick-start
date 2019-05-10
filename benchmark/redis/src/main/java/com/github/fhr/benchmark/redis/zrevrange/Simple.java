package com.github.fhr.benchmark.redis.zrevrange;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Fan Huaran
 * created on 2019/5/8
 * @description
 */
public class Simple {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-config-redis.xml");

        final RedisUtils redisUtils = applicationContext.getBean(RedisUtils.class);

        for (int i = 0; i < 100; i++) {
            redisUtils.hset("black.sku.hash", String.valueOf(i), String.valueOf(i));
        }

        String[] params;
        {
            params = new String[50];
            for (int i = 0; i < 50; i++) {
                params[i] = String.valueOf(i);
            }
        }
        final AtomicLong sum = new AtomicLong(0L);
        int threadNum = 200;
        int workNum = 200;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum);
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < workNum; j++) {
                    long start = System.currentTimeMillis();
                    redisUtils.hmget("black.sku.hash", params);
//                    redisUtils.get("1");
                    long cost = System.currentTimeMillis() - start;
                    sum.addAndGet(cost);
                }
//                System.out.println("cost:" + (System.currentTimeMillis() - start));
                countDownLatch.countDown();
            });
            thread.start();
        }

        try {
            countDownLatch.await();
            System.out.println("sum:" + sum.get());
            System.out.println("avg:" + sum.get() / (threadNum * workNum));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
