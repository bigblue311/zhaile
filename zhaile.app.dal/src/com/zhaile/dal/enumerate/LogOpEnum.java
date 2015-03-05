package com.zhaile.dal.enumerate;

import java.util.List;

import com.google.common.collect.Lists;

public enum LogOpEnum {
	//0: login 1:search 2:tag 3:shoppingCar 4:transaction 5:payment 6:comment 7:update 8:register
	登录("0","登录"),
	搜索("1","搜索"),
	贴标签("2","贴标签"),
	购物车("3","购物车"),
	交易("4","交易"),
	支付("5","支付"),
	评论("6","评论"),
	更新("7","更新"),
	注册("8","注册"),
	积分("9","积分"),
	送达("10","送达"),
	收藏("11","收藏"),
	取消收藏("12","取消收藏"),
	充值("13","充值"),
	使用卡券("14","使用卡券"),
	退款("15","退款"),
	购买卡券("16","购买卡券"),
	后台发卡("17","后台发卡"),
	满就送("18","满就送");
	
	private String code;
	private String desc;
	
	private LogOpEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}
	
	public static List<LogOpEnum> getAll(){
		return Lists.newArrayList(LogOpEnum.values());
	}
	
	public static LogOpEnum getByCode(String code){
		for(LogOpEnum userOp : getAll()){
			if(userOp.code.equals(code)){
				return userOp;
			}
		}
		return null;
	}
	
	public static LogOpEnum getByDesc(String desc){
		for(LogOpEnum userOp : getAll()){
			if(userOp.desc.equals(desc)){
				return userOp;
			}
		}
		return null;
	}
	
	public static LogOpEnum getByText(String text){
		if(getByCode(text) == null) {
			return getByDesc(text);
		}
		return getByCode(text);
	}
}
