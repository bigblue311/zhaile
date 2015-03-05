package com.zhaile.dal.test;

import com.victor.framework.common.shared.Result;
import com.zhaile.dal.dao.CustomerCreditDAO;
import com.zhaile.dal.dao.impl.CustomerCreditDAOImpl;
import com.zhaile.dal.model.CustomerCreditDO;

public class CustomerCreditDAOTest {
	public static void main(String[] args){
		CustomerCreditDAO dao = new CustomerCreditDAOImpl();
		Result<CustomerCreditDO> result = dao.gainCredits(11l, 5);
		System.out.println(result);
		Result<CustomerCreditDO> result2 = dao.useCredits(11l, 4);
		System.out.println(result2);
	}
}
