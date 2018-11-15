package com.fhr.zookeeper;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by Huaran Fan on 2018/11/3
 *
 * @description
 */
public class SimpleCuratorLock {

	private final String lockPath;

	private final CuratorFramework curatorFramework;

	public SimpleCuratorLock(String lockPath, CuratorFramework curatorFramework) {
		this.lockPath = lockPath;
		this.curatorFramework = curatorFramework;
	}

	public void lock() throws Exception {
		curatorFramework.create()
						.withMode(CreateMode.EPHEMERAL)
						.forPath(lockPath);
	}
}
