package com.zhaile.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.tools.LogTools;
import com.zhaile.biz.common.Cache;

public class RefreshCacheTask extends ScheduledTask{

	private static LogTools log = new LogTools(RefreshCacheTask.class);
	
	public RefreshCacheTask() {
		super(1L,TimeUnit.HOURS);
	}
	
	@Autowired
	private Cache cache;
	
	@Override
	public void doWork() {
		cache.reload();
		System.out.println("缓存刷新成功");
		log.error("缓存刷新成功");
	}
}
