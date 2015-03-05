package com.victor.framework.batch.thread;


public abstract class ExecutorTask implements Runnable{
	
	public abstract void doWork();
	
	@Override
	public void run() {
		doWork();
	}
}
