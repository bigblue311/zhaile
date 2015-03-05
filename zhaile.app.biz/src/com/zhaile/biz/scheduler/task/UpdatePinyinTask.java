package com.zhaile.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.tools.LogTools;
import com.zhaile.dal.cache.CategoryCache;

public class UpdatePinyinTask extends ScheduledTask {

	private static LogTools log = new LogTools(UpdatePinyinTask.class);
	
	@Autowired
	CategoryCache categoryCache;
	
	public UpdatePinyinTask() {
		super(12L,TimeUnit.HOURS);
	}

	@Override
	public void doWork() {
		categoryCache.reindex();
		log.error("更新拼音完成");
	}

}
