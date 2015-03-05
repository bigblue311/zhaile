package com.zhaile.biz.scheduler.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.ShoppingCarDAO;

/**
 * 如果商品下架了，那么回收该商品从购物车中
 * @author hadoop
 *
 */
public class RecycleShoppingCarTask extends ScheduledTask{

	private static LogTools log = new LogTools(RecycleShoppingCarTask.class);
	
	public RecycleShoppingCarTask() {
		super(120, TimeUnit.MINUTES);
	}

	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ShoppingCarDAO shoppingCarDAO;
	
	@Override
	public void doWork() {
		Result<List<Long>> result = productDAO.getDisabledList();
		if(!result.isSuccess() || CollectionTools.isEmpty(result.getDataObject())){
			log.error("获取失效店铺数据失败");
			return;
		}
		List<Long> prodList = result.getDataObject();
		Result<Boolean> recycleResult = shoppingCarDAO.recycle(prodList.toArray(new Long[prodList.size()]));
		System.out.println("回收客户购物车任务执行结果:"+recycleResult.isSuccess());
		log.error("回收客户购物车任务执行结果:"+recycleResult.isSuccess());
	}
}
