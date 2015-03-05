package com.victor.framework.search.basic;

public class KeyWord {
	private String keyword = "";
	private String shopName = "";
	private String price = "";
	
	public KeyWord(String keyword,String shopName,String price){
		this.keyword = keyword;
		this.shopName = "来自"+shopName;
		this.price = price+"元";
	}
	
	public KeyWord(String keyword,String shopName){
		this.keyword = keyword;
		this.shopName = "来自"+shopName;
		this.price = "商家";
	}

	public String getKeyword() {
		return keyword;
	}

	public String getShopName() {
		return shopName;
	}

	public String getPrice() {
		return price;
	}
}
