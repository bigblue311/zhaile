package com.zhaile.admin.webpage.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.zhaile.biz.common.Cache;
import com.zhaile.dal.model.EmployeeDO;

public class UpdateEmployeeAction {
	@Autowired
	private Cache cache;
	
	public void doUpdate(@FormGroup("employee") EmployeeDO employeeDO, Navigator nav,Context context) {
		if(employeeDO.getId() == null ) {
			cache.insert2DB(employeeDO);
		} else {
			cache.update2DB(employeeDO);
		}
		cache.reload();
		nav.redirectTo("admin").withTarget("employee.vm");
	}
	
	public void doDelete(@FormGroup("employee") EmployeeDO employeeDO, Navigator nav,Context context) {
		cache.deleteEmployee(employeeDO.getId());
		cache.reload();
		nav.redirectTo("admin").withTarget("employee.vm");
	}
}
