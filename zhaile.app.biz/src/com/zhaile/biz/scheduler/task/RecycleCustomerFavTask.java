package com.zhaile.biz.scheduler.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.zhaile.dal.dao.CustomerFavDAO;
import com.zhaile.dal.dao.ShopDAO;

/**
 * 如果店铺下架了，那么回收该商品从收藏夹中
 * @author hadoop
 *
 */
public class RecycleCustomerFavTask extends ScheduledTask{

	private static LogTools log = new LogTools(RecycleCustomerFavTask.class);
	
	public RecycleCustomerFavTask() {
		super(240L,TimeUnit.MINUTES);
	}
	
	@Autowired
	private ShopDAO shopDAO;
	
	@Autowired
	private CustomerFavDAO customerFavDAO;
	
	@Override
	public void doWork() {
		Result<List<Long>> result = shopDAO.getDisabledList();
		if(!result.isSuccess() || CollectionTools.isEmpty(result.getDataObject())){
			log.error("获取失效店铺数据失败");
			return;
		}
		List<Long> shopList = result.getDataObject();
		Result<Boolean> recycleResult = customerFavDAO.recycle(shopList.toArray(new Long[shopList.size()]));
		System.out.println("回收客户收藏夹任务执行结果:"+recycleResult.isSuccess());
		log.error("回收客户收藏夹任务执行结果:"+recycleResult.isSuccess());
	}	
}
