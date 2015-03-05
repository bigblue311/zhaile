package com.victor.framework.batch.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class ScheduledExecutor<T extends ScheduledTask> extends Executor<T> {

	private final int MAX_CORE_POOL_SIZE = 5;
	
	private ScheduledExecutorService service = Executors.newScheduledThreadPool(MAX_CORE_POOL_SIZE);
	
	@Override
	protected void doStart() {
		if(service.isShutdown()){
			service = Executors.newScheduledThreadPool(MAX_CORE_POOL_SIZE);	
		}
		while(!super.isEmpty()){
			T task = super.Dequeue();
			service.scheduleWithFixedDelay(task, 0, task.getDelay(), task.getTimeUnit());
		}
	}

	@Override
	protected void doStop() {
		service.shutdown();
	}

}
