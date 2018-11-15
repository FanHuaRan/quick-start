package com.fhr.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by Huaran Fan on 20阿转载18/11/4
 *
 * @description
 */
public class CuratorStandLock {

	public static void main(String[] args) throws Exception {
		String connectionString = "localhost:2181"; // 连接字符串
		int sessionTimeoutMs = 60 * 1000; // session过期时间
		int connectionTimeoutMs = 60 * 1000; // 连接过期时间
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3); // 重试策略

		// 创建一个CuratorFramework
		try (CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient(connectionString,
				sessionTimeoutMs,
				connectionTimeoutMs,
				retryPolicy)) {

			curatorFramework.start();// 开启zookeeper客户端
			// 锁路径
			String lockPath = "/try_lock";
			// 创建锁
			InterProcessMutex interProcessMutex = new InterProcessMutex(curatorFramework, lockPath);
			// 获取锁
			interProcessMutex.acquire();
			try {
				// do some thing;
			} finally {
				interProcessMutex.release();//释放锁
			}
		}

	}

}
