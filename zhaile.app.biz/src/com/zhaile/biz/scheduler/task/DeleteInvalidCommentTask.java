package com.zhaile.biz.scheduler.task;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.tools.LogTools;
import com.zhaile.dal.dao.CustomerCommentDAO;

public class DeleteInvalidCommentTask extends ScheduledTask{

private static LogTools log = new LogTools(RecycleCustomerFavTask.class);
	
	public DeleteInvalidCommentTask() {
		super(60L,TimeUnit.MINUTES);
	}
	
	@Autowired
	private CustomerCommentDAO customerCommentDAO;
	
	@Override
	public void doWork() {
		System.out.println("删除非法评论结果:"+customerCommentDAO.deleteInvalid());
		log.error("删除非法评论结果:"+customerCommentDAO.deleteInvalid());
	}
}
