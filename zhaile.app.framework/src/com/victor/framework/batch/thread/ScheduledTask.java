package com.victor.framework.batch.thread;

import java.util.concurrent.TimeUnit;

public abstract class ScheduledTask extends ExecutorTask {
	private long delay;
	private TimeUnit timeunit;
	
	public ScheduledTask(long delay, TimeUnit timeunit){
		this.delay = delay;
		this.timeunit = timeunit;
	}
	
	public ScheduledTask(String taskName){
		this.delay = 60l;
		this.timeunit = TimeUnit.SECONDS;
	}
	
	protected long getDelay(){
		return this.delay;
	}
	
	protected TimeUnit getTimeUnit(){
		return this.timeunit;
	}
}
