package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum ShopCategoryEnum {
	商家自送(0l,"商家自送"),
	中式简餐(1l,"中式简餐"),
	中式炒菜(2l,"中式炒菜"),
	西式日韩(3l,"西式日韩"),
	甜点小食(4l,"甜点小食");
	
	private Long id;
	private String desc;
	
	private ShopCategoryEnum(Long id,String desc){
		this.id = id;
		this.desc = desc;
	}

	public Long getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<ShopCategoryEnum> getAll(){
		return Lists.newArrayList(ShopCategoryEnum.values());
	}
	
	public static ShopCategoryEnum getById(Long id){
		for(ShopCategoryEnum source : getAll()){
			if(source.id == id){
				return source;
			}
		}
		return null;
	}
	
	public static ShopCategoryEnum getByDesc(String desc){
		for(ShopCategoryEnum source : getAll()){
			if(source.desc.equals(desc)){
				return source;
			}
		}
		return null;
	}
}
