package com.zhaile.dal.cache;

import com.zhaile.dal.model.EmployeeDO;

public interface EmployeeCache {
	void reload();
	
	EmployeeDO getEmployee(String key);
	
	EmployeeDO getEmployee(Long key);
	
	void updateDB(EmployeeDO employeeDO);
	
	void insertDB(EmployeeDO employeeDO);
	
	void deleteById(Long id);
}
