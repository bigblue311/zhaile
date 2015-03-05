package com.victor.framework.test;

import com.victor.framework.batch.thread.ScheduledExecutor;

public class ScheduleExecutorTest {
	
	public static void main(String[] args) throws InterruptedException{
		final ScheduledExecutor<ScheduledTask4Test> se = new ScheduledExecutor<ScheduledTask4Test>();
		
		ScheduledTask4Test t1 = new ScheduledTask4Test();
		ScheduledTask4Test t2 = new ScheduledTask4Test();
		ScheduledTask4Test t3 = new ScheduledTask4Test();
		
		se.Enqueue(t1);
		se.Enqueue(t2);
		se.Enqueue(t3);
		se.start();
		System.out.println("finished");
	}
}
