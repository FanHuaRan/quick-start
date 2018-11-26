package com.fhr.zookeeper.helloworld;

import java.util.concurrent.TimeUnit;

/**
 * Created by Huaran Fan on 2018/11/7
 *
 * @description
 */
public interface InterProcessLock
{
    /**
     * 获取锁（请求互斥量），阻塞直到锁可用，获取锁之后需要调用release方法释放
     *
     * @throws Exception zookeeper错误或者连接中断
     */
     void acquire() throws Exception;

    /**
     *  获取锁（请求互斥量），阻塞直到锁可用或者超时，获取锁之后需要调用release方法释放
     *
     * @param time time to wait
     * @param unit time unit
     * @return 返回true代表获取到锁，false代表未获取
     * @throws Exception zookeeper错误或者连接中断
     */
     boolean acquire(long time, TimeUnit unit) throws Exception;

    /**
     * 释放锁
     *
     * @throws Exception zookeeper错误、连接中断或者当前线程不持有锁
     */
     void release() throws Exception;

    /**
     * 判断当前线程是否持有锁
     *
     * @return true/false
     */
    boolean isAcquiredInThisProcess();
}
