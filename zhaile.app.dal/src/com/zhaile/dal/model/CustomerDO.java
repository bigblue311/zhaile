package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.common.tools.MD5;
import com.victor.framework.common.tools.StringTools;
import com.victor.framework.dal.basic.EntityDO;
import com.zhaile.dal.enumerate.ForeignKeyEnum;
import com.zhaile.dal.enumerate.ThirdPartUserEnum;

public class CustomerDO extends EntityDO implements Serializable {
	private static final long serialVersionUID = -5619117788675400838L;
	private String  name;			//登录名
	private String 	nick;			//用户名
	private String 	password; 		//密码
	private String 	headImage; 		//头像
	private String  thirdPartUser;	//第三方用户
	private String  thirdPartUserId;//第三方用户ID
	private String  email;			//Emial
	private String  mobile;			//手机号码
	
	public ForeignKeyEnum getForeignKeyType(){
		return ForeignKeyEnum.用户;
	}
	
	public void translate(){
		this.thirdPartUser = ThirdPartUserEnum.getByCode(this.thirdPartUser)==null?this.thirdPartUser:ThirdPartUserEnum.getByCode(this.thirdPartUser).getDesc();
		this.password = "";
	}
	
	public void format(){
		this.thirdPartUser = ThirdPartUserEnum.getByDesc(this.thirdPartUser)==null?this.thirdPartUser:ThirdPartUserEnum.getByDesc(this.thirdPartUser).getCode();
		this.password = "";
	}
	
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		if(StringTools.isNotEmpty(password)){
			this.password = MD5.getMD5(password.getBytes());
		}
	}
	public String getHeadImage() {
		if(StringTools.isEmpty(headImage)){
			return "./img/headimg/default_headImg.jpg";
		}
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getThirdPartUser() {
		return thirdPartUser;
	}
	public void setThirdPartUser(String thirdPartUser) {
		this.thirdPartUser = thirdPartUser;
	}
	public String getThirdPartUserId() {
		return thirdPartUserId;
	}
	public void setThirdPartUserId(String thirdPartUserId) {
		this.thirdPartUserId = thirdPartUserId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
