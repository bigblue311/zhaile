package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.common.tools.MD5;
import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.ForeignKeyEnum;

public class EmployeeDO extends EntityDO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5816271381654263942L;
	private String  name;			//登录名
	private String 	password; 		//密码
	private String  mobile;			//手机号码
	private String  role;			//角色
	
	public ForeignKeyEnum getForeignKeyType(){
		return ForeignKeyEnum.员工;
	}
	
	public void translate(){
		this.password = "";
	}
	
	public void format(){
		this.password = "";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = MD5.getMD5(password.getBytes());
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
