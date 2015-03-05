package com.zhaile.biz.web.model;

import com.victor.framework.common.tools.DateTools;
import com.zhaile.biz.web.enumerate.TodayEventStatusEnum;
import com.zhaile.dal.model.FlashGoDO;

public class TodayEventVO {
	private FlashGoDO flashGo;
	
	private String status;
	
	private Boolean valid;
	
	private Integer itemLeft;
	
	private Integer itemAllowed;
	
	private Boolean enable;

	public FlashGoDO getFlashGo() {
		return flashGo;
	}

	public void setFlashGo(FlashGoDO flashGo) {
		this.flashGo = flashGo;
		if(flashGo == null) {
			itemLeft = 0;
			itemAllowed = 0;
			status = TodayEventStatusEnum.无效.getCode();
			valid = TodayEventStatusEnum.无效.getValid();
			enable = false;
			return;
		}
		itemLeft = flashGo.getTotal() - flashGo.getSold();
		if(itemLeft <= 0){
			itemAllowed = 0;
			status = TodayEventStatusEnum.售罄.getCode();
			valid = TodayEventStatusEnum.售罄.getValid();
			enable = false;
			return;
		}
		if(flashGo.getGmtOpen().after(DateTools.today())){
			itemAllowed = 0;
			status = TodayEventStatusEnum.未开始.getCode();
			valid = TodayEventStatusEnum.未开始.getValid();
			enable = false;
			return;
		}
		if(flashGo.getGmtClose().after(DateTools.today())){
			itemAllowed = flashGo.getLimitBuy() >= itemLeft ? itemLeft : flashGo.getLimitBuy();
			status = TodayEventStatusEnum.进行中.getCode();
			valid = TodayEventStatusEnum.进行中.getValid();
			enable = true;
			return;
		} else {
			itemAllowed = 0;
			status = TodayEventStatusEnum.已结束.getCode();
			valid = TodayEventStatusEnum.已结束.getValid();
			enable = false;
			return;
		}
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	public Integer getItemLeft() {
		return itemLeft;
	}

	public void setItemLeft(Integer itemLeft) {
		this.itemLeft = itemLeft;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public Integer getItemAllowed() {
		return itemAllowed;
	}

	public void setItemAllowed(Integer itemAllowed) {
		this.itemAllowed = itemAllowed;
	}
}
