package com.fhr.quartzhelloworld;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
/**
 * @description:
 * @author:
 * @create: 2018-03-24 13:52
 **/
public class HelloJob implements  Job {

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("hello world");
    }
}
