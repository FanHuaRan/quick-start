package com.fhr.zookeeper;

import org.apache.zookeeper.*;

/**
 * Created by Huaran Fan on 2018/11/3
 *
 * @description
 */
public class SimpleLock {

	private String lockNameSpace;

	private String lock;

	private String lockNode;

	private ZooKeeper zooKeeper;

	public void lock() throws InterruptedException {
		ensureRootPath();
		watchNode(lockNode, Thread.currentThread());
		String path = null;
		while (true){
			try {
				path = zooKeeper.create(lockNode, "".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
			} catch (KeeperException e) {
				e.printStackTrace();
//				Thread.sleep("5000  ");
			}
		}

	}

	private void ensureRootPath() throws InterruptedException {
		try {
			if (zooKeeper.exists(lockNameSpace, true) == null) {
				zooKeeper.create(lockNameSpace, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			}
		} catch (KeeperException er) {
			er.printStackTrace();
		}
	}
	private void watchNode(String nodeString, final Thread thread) throws InterruptedException {
		try {
			zooKeeper.exists(nodeString, watchedEvent1 -> {
				if(watchedEvent1.getType() == Watcher.Event.EventType.NodeDeleted){
					thread.interrupt();
				}

				try {
					zooKeeper.exists(nodeString, watchedEvent2 -> {
						if(watchedEvent2.getType() == Watcher.Event.EventType.NodeDeleted){
							thread.interrupt();
						}
						try {
							zooKeeper.exists(nodeString,true);
						} catch (KeeperException e) {
							e.printStackTrace();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					});
				} catch (KeeperException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}

	public void tryLock(){

	}

}
