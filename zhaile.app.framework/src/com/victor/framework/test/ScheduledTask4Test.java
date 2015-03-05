package com.victor.framework.test;

import java.util.concurrent.TimeUnit;

import com.victor.framework.batch.thread.ScheduledTask;

public class ScheduledTask4Test extends ScheduledTask{

	public ScheduledTask4Test() {
		super(2,TimeUnit.SECONDS);
	}

	@Override
	public void doWork() {
		System.out.println(Thread.currentThread().getName()+" is running");
	}

}
