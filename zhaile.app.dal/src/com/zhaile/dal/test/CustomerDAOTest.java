package com.zhaile.dal.test;

import com.victor.framework.common.tools.LogTools;
import com.zhaile.dal.dao.CustomerDAO;
import com.zhaile.dal.dao.impl.CustomerDAOImpl;
import com.zhaile.dal.model.CustomerDO;

public class CustomerDAOTest {
	private static LogTools log = new LogTools(CustomerDAOTest.class);
	
	public static void main(String[] args){		
		CustomerDAO customerDAO = new CustomerDAOImpl();
		CustomerDO customerDO = new CustomerDO();
		customerDO.setId(1l);
		customerDO.setNick("victor");
		customerDO.setPassword("123456");
		customerDO.setHeadImage("1.jpg");
		//customerDAO.insert(customerDO);
		customerDO.setHeadImage("2.jpg");
		customerDAO.update(customerDO);
		CustomerDO newDO = customerDAO.getById(1l).getDataObject();
		//System.out.println(newDO);
		log.info("打印bean信息", newDO);
		log.info(customerDAO.checkExistName("victor2").getMessage());
		log.info(customerDAO.loginByName("victor", "1234567").getMessage());
	}
}
