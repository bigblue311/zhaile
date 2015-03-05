package com.zhaile.biz.web.model;

import java.io.Serializable;

import com.victor.framework.common.tools.DateTools;
import com.zhaile.dal.model.FlashGoDO;
import com.zhaile.dal.model.ProductDO;
import com.zhaile.dal.model.ShopDO;

public class FlashGoVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4190762519573710212L;
	private FlashGoDO flashGo;
	private ProductDO prod;
	private ShopDO shop;
	
	public FlashGoDO getFlashGo() {
		return flashGo;
	}
	public void setFlashGo(FlashGoDO flashGo) {
		this.flashGo = flashGo;
	}
	public ProductDO getProd() {
		return prod;
	}
	public void setProd(ProductDO prod) {
		this.prod = prod;
	}
	public ShopDO getShop() {
		return shop;
	}
	public void setShop(ShopDO shop) {
		this.shop = shop;
	}
	public Long getTimeLeft() {
		if(flashGo == null) {
			return -99*60l*60l*1000l;
		}
		return flashGo.getGmtOpen().getTime() - DateTools.today().getTime();
	}
	public Long getTimeLast(){
		if(flashGo == null) {
			return -1l;
		}
		return flashGo.getGmtClose().getTime() - flashGo.getGmtOpen().getTime();
	}
	public Integer getItemLeft() {
		if(flashGo == null) {
			return 0;
		}
		return flashGo.getTotal() - flashGo.getSold();
	}
	public String getStyle() {
		String value = "before";
		if(flashGo==null){
			return value;
		}
		int todayWeek = DateTools.weekDay();
		
		if(DateTools.weekDay(flashGo.getGmtOpen())<todayWeek){
			value = "before";
		}
		if(DateTools.weekDay(flashGo.getGmtOpen()) == todayWeek){
			value = "current";
			if(flashGo.getGmtOpen().before(DateTools.today()) && flashGo.getGmtClose().after(DateTools.today())){
				value += " flashProduct";
			}
		}
		if(DateTools.weekDay(flashGo.getGmtOpen()) > todayWeek){
			value = "after";
		}
		return value;
	}
	
	public String getLabel(){
		String label = "往期活动";
		if(flashGo==null){
			return label;
		}
		
		if(flashGo.getGmtOpen().before(DateTools.today())){
			label = "往期活动";
		}else {
			label = "即将开始";
		}
		return label;
	}
}
