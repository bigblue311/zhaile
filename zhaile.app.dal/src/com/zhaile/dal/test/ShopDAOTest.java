package com.zhaile.dal.test;

import com.victor.framework.common.tools.LogTools;
import com.zhaile.dal.dao.ShopDAO;
import com.zhaile.dal.dao.impl.ShopDAOImpl;
import com.zhaile.dal.model.ShopDO;
import com.zhaile.dal.query.condition.ShopQueryCondition;

public class ShopDAOTest {
private static LogTools log = new LogTools(ShopDAOTest.class);
	
	public static void main(String[] args){		
		ShopDAO shopDAO = new ShopDAOImpl();
		ShopDO shopDO = new ShopDO();
		shopDO.setId(1l);
		shopDO.setName("吉祥");
		shopDO.setCustomerId(1l);
		shopDO.setGmtOpen("8:00:00");
		shopDO.setGmtClose("12:00:00");
		System.out.println(shopDO.isValid());
		shopDAO.insert(shopDO);
		//System.out.println(newDO);
		ShopQueryCondition queryCondition = new ShopQueryCondition();
		queryCondition.keyword("吉祥");
		log.info("打印bean信息", shopDAO.getPage(queryCondition));
	}
}
