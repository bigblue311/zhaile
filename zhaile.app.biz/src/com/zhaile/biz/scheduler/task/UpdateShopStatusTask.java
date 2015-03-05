package com.zhaile.biz.scheduler.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.victor.framework.batch.thread.ScheduledTask;
import com.victor.framework.common.shared.Result;
import com.victor.framework.common.tools.CollectionTools;
import com.victor.framework.common.tools.LogTools;
import com.zhaile.dal.dao.ProductDAO;
import com.zhaile.dal.dao.ShopDAO;

public class UpdateShopStatusTask extends ScheduledTask{

	private static LogTools log = new LogTools(UpdateShopStatusTask.class);
	
	public UpdateShopStatusTask() {
		super(60L,TimeUnit.MINUTES);
	}
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ShopDAO shopDAO;

	@Override
	public void doWork() {
		autoDisable();
		autoEnable();
	}
	
	private void autoDisable(){
		Result<List<Long>> result = productDAO.getDisabledList();
		if(!result.isSuccess() || CollectionTools.isEmpty(result.getDataObject())){
			log.error("获取失效店铺数据失败");
			return;
		}
		List<Long> shopList = result.getDataObject();
		Result<Boolean> recycleResult = shopDAO.autoDisable(shopList.toArray(new Long[shopList.size()]));
		System.out.println("自动禁用店铺执行结果:"+recycleResult.isSuccess());
		log.error("自动禁用店铺执行结果:"+recycleResult.isSuccess());
	}
	
	private void autoEnable(){
		Result<List<Long>> result = productDAO.getAnyEnabled();
		if(!result.isSuccess() || CollectionTools.isEmpty(result.getDataObject())){
			log.error("获取失效店铺数据失败");
			return;
		}
		List<Long> shopList = result.getDataObject();
		Result<Boolean> recycleResult = shopDAO.autoEnable(shopList.toArray(new Long[shopList.size()]));
		System.out.println("自动起用店铺执行结果:"+recycleResult.isSuccess());
		log.error("自动起用店铺执行结果:"+recycleResult.isSuccess());
	}
}
