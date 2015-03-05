package com.zhaile.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.tools.LogTools;
import com.zhaile.biz.web.manager.FileUploadManager;

public class RecycleTempFilesTask extends ScheduledTask{

	private static LogTools log = new LogTools(RecycleTempFilesTask.class);
	
	public RecycleTempFilesTask() {
		super(1L,TimeUnit.DAYS);
	}
	
	@Autowired
	private FileUploadManager fileUploadManager;
	
	@Override
	public void doWork() {
		fileUploadManager.recycleTemp();
		System.out.println("自动回收零时文件执行完成");
		log.error("自动回收零时文件执行完成");
	}
}
