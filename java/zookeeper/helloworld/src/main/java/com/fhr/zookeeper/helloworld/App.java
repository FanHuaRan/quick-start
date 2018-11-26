package com.fhr.zookeeper.helloworld;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Huaran Fan on 2018/9/5
 *
 * @description
 */
public class App {
	public static void main(String []args) throws InterruptedException, ExecutionException {
		ForkJoinPool forkJoinPool=new ForkJoinPool();
		CountTask countTask=new CountTask(0, 212918392);
		forkJoinPool.invoke(countTask);
		Future<Integer> result=forkJoinPool.submit(countTask);
		System.out.print(result.get());
	}
}

 class CountTask extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 4699632421974478868L;
	private static final int THRESHOLD=2;//阈值
	private final int start;
	private final int end;
	public CountTask(int start,int end) {
		this.start=start;
		this.end=end;
	}
	@Override
	protected Integer compute() {
		int sum=0;
//如果任务足够小就计算任务
		boolean canCompute=(end-start)<=THRESHOLD;
		if(canCompute){
			for(int i=start;i<=end;i++){
				sum+=i;
			}
		}else{//如果任务大于阈值，就分裂成两个子任务计算
			int middle=(start+end)>>1;
			CountTask leftTask=new CountTask(start, middle);
			CountTask rightTask=new CountTask(middle, end);
			leftTask.fork();
			rightTask.fork();
			int leftResult=leftTask.join();
			int rightResult=rightTask.join();
			sum=leftResult+rightResult;
		}
		return sum;
	}
}
