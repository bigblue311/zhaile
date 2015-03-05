package com.zhaile.dal.model;

import java.io.Serializable;

import com.victor.framework.dal.basic.EntityDO;

public class HeadImageDO extends EntityDO implements Serializable {
	private static final long serialVersionUID = 2908696978466547887L;
	private String imgS;	//小图片地址
	private String imgM;	//中等图片地址
	private String imgL;	//大图片地址
	private String title;	//标题
	public String getImgS() {
		return imgS;
	}
	public void setImgS(String imgS) {
		this.imgS = imgS;
	}
	public String getImgM() {
		return imgM;
	}
	public void setImgM(String imgM) {
		this.imgM = imgM;
	}
	public String getImgL() {
		return imgL;
	}
	public void setImgL(String imgL) {
		this.imgL = imgL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
