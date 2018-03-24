package com.fhr.quartzhelloworld;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws  SchedulerException{

        // 调度工厂
        SchedulerFactory sf = new StdSchedulerFactory();

        // 从工厂中，获取一个任务调度实体
        Scheduler sched = sf.getScheduler();

        // 定义任务运行时间，这里的话，你需要改成你想要任务在什么时候执行
        Date runTime = new Date();
        System.out.println("任务将执行");

        // 初始化任务实体
        JobDetail jobDetail = JobBuilder
                .newJob(HelloJob.class)
                .withIdentity("jobdetail1", "group1")
                .build();

        // 初始化触发器
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(runTime)
                .build();

        // 设置定时任务
        sched.scheduleJob(jobDetail, trigger);

        // 启动定时任务
        sched.start();

        try {
            Thread.sleep(300000L);
        } catch (Exception e) {
        }

        // 停止
        sched.shutdown(true);
    }
}
