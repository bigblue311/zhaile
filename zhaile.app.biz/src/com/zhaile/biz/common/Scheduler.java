package com.zhaile.biz.common;

import java.util.List;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.Lists;
import com.victor.framework.batch.thread.ScheduledExecutor;
import com.victor.framework.batch.thread.ScheduledTask;

public class Scheduler implements InitializingBean,DisposableBean {
	
	final ScheduledExecutor<ScheduledTask> se = new ScheduledExecutor<ScheduledTask>();
	
	List<ScheduledTask> tasks = Lists.newArrayList();
	
	public void run(){
		for(ScheduledTask task : tasks){
			task.run();
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		for(ScheduledTask task : tasks){
			se.Enqueue(task);
		}
		se.start();
	}

	@Override
	public void destroy() throws Exception {
		se.stop();
	}

	public void setTasks(List<ScheduledTask> tasks) {
		this.tasks = tasks;
	}
}
